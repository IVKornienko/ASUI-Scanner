package com.ivkornienko.asui.scanner

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.zxing.client.android.Intents
import com.ivkornienko.asui.scanner.databinding.FragmentScannerBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class ScannerFragment : Fragment(R.layout.fragment_scanner) {

    private lateinit var binding: FragmentScannerBinding
    private val viewModel: ScannerViewModel by viewModels()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentScannerBinding.bind(view)

        setListeners()
        observeViewModels()
    }

    fun setListeners() {
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

    }

    fun observeViewModels() {
        viewModel.state.observe(viewLifecycleOwner) {
            onDefaultViews()
            when (it) {
                is ScannerViewModel.EmptyBarcode -> {
                    binding.tilBarcode.error = "Barcode is empty"
                }
                is ScannerViewModel.Error -> {
                    if (it.error.isNotBlank()) {
                        Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    }
                }
                is ScannerViewModel.Progress -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.tilBarcode.isEnabled = false
                    binding.buttonGetFromScanner.isEnabled = false
                    binding.buttonReadBarcode.isEnabled = false
                }
                is ScannerViewModel.Success -> {
                    val productInfo = it.result
                    openDetailFragment(productInfo)
                }
                is ScannerViewModel.Empty -> {}
            }
        }
    }

    private fun openDetailFragment(productInfo: ProductInfo) {
        viewModel.clear_state()
        val direction =
            ScannerFragmentDirections.scannerFragmentToInfoByBarcodeFragment(productInfo)
        findNavController().navigate(direction)
    }

    private fun onDefaultViews() {
        binding.progressBar.visibility = View.GONE
        binding.tilBarcode.error = null

        binding.tilBarcode.isEnabled = true
        binding.buttonGetFromScanner.isEnabled = true
        binding.buttonReadBarcode.isEnabled = true
    }

}