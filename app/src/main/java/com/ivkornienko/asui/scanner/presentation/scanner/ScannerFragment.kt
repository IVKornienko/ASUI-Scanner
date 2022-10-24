package com.ivkornienko.asui.scanner.presentation.scanner

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.zxing.client.android.Intents
import com.ivkornienko.asui.scanner.AsuiScannerApplication
import com.ivkornienko.asui.scanner.R
import com.ivkornienko.asui.scanner.collectOnce
import com.ivkornienko.asui.scanner.databinding.FragmentScannerBinding
import com.ivkornienko.asui.scanner.presentation.ViewModelFactory
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScannerFragment : Fragment(R.layout.fragment_scanner) {

    private var _binding: FragmentScannerBinding? = null
    private val binding: FragmentScannerBinding
        get() = _binding ?: throw RuntimeException("FragmentScannerBinding is Nullable")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ScannerViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as AsuiScannerApplication).component
    }

    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            val originalIntent = result.originalIntent
            if (originalIntent == null) {
                Toast.makeText(context, getString(R.string.scanning_cancelled), Toast.LENGTH_LONG)
                    .show()
            } else if (originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION)) {
                Toast.makeText(
                    context,
                    getString(R.string.scanning_not_permissions),
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            binding.etBarcode.setText(result.contents)
            viewModel.getInfoByBarcode(result.contents)
        }
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        observeViewModels()
    }

    private fun setListeners() {
        binding.buttonGetFromScanner.setOnClickListener {
            val options = ScanOptions().apply {
                setOrientationLocked(false)
                captureActivity = ScanningActivity::class.java
            }
            barcodeLauncher.launch(options)
        }

        binding.buttonReadBarcode.setOnClickListener {
            val barcode = binding.etBarcode.text.toString()
            viewModel.getInfoByBarcode(barcode)
        }

        binding.etBarcode.addTextChangedListener {
            binding.tilBarcode.error = null
        }
    }

    private fun observeViewModels() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectOnce(viewModel::setStateValue) {
                updateUI(it)
            }
        }
    }

    private fun updateUI(state: ScannerViewModel.State?) {
        if (state == null) return
        onDefaultViews()
        when (state) {
            is ScannerViewModel.EmptyBarcode -> {
                binding.tilBarcode.error = getString(R.string.barcode_empty)
            }
            is ScannerViewModel.Error -> {
                if (state.error.isNotBlank()) {
                    Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
                }
            }
            is ScannerViewModel.Progress -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.tilBarcode.isEnabled = false
                binding.buttonGetFromScanner.isEnabled = false
                binding.buttonReadBarcode.isEnabled = false
            }
            is ScannerViewModel.Success -> {
                val productInfoId = state.result
                openDetailFragment(productInfoId)
            }
            ScannerViewModel.EmptyHost -> {
                Toast.makeText(requireContext(), getString(R.string.empty_host_scanner), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openDetailFragment(productInfoId: Long) {
        val direction =
            ScannerFragmentDirections.scannerFragmentToInfoByBarcodeFragment(
                productInfoId
            )
        findNavController().navigate(direction)
    }

    private fun onDefaultViews() {
        binding.progressBar.visibility = View.GONE
        binding.tilBarcode.error = null

        binding.etBarcode.setText("")
        binding.tilBarcode.isEnabled = true
        binding.buttonGetFromScanner.isEnabled = true
        binding.buttonReadBarcode.isEnabled = true
    }

}