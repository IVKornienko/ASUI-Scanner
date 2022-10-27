package com.ivkornienko.asui.scanner.presentation.settings

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.ivkornienko.asui.scanner.AsuiScannerApplication
import com.ivkornienko.asui.scanner.R
import com.ivkornienko.asui.scanner.collectOnce
import com.ivkornienko.asui.scanner.databinding.FragmentSettingsBinding
import com.ivkornienko.asui.scanner.presentation.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding ?: throw RuntimeException("FragmentSettingsBinding is Nullable")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[SettingsViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as AsuiScannerApplication).component
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
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createMenu()
        setListeners()
        observeViewModels()

        if (savedInstanceState == null) viewModel.loadSettings()
    }


    private fun createMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.settings_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.item_defaultSettings -> {
                        viewModel.defaultSettings()
                        return false
                    }
                }
                return true
            }
        }, viewLifecycleOwner)
    }

    private fun setListeners() {
        binding.buttonTestConnection.setOnClickListener {
            executeListenersButton(viewModel::testSettings)
        }

        binding.buttonCancel.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

        binding.buttonSave.setOnClickListener {
            executeListenersButton(viewModel::saveSettings)
        }

        binding.etHost1C.addTextChangedListener {
            binding.buttonSave.isEnabled = false
            binding.tilHost1C.error = null
        }
        binding.etLogin1C.addTextChangedListener {
            binding.buttonSave.isEnabled = false
            binding.tilLogin1C.error = null
        }
    }

    private fun executeListenersButton(func: (String, String, String, String, String) -> Unit) {
        val host = binding.etHost1C.text.toString()
        val base = binding.etBase1C.text.toString()
        val name = binding.etName1C.text.toString()
        val login = binding.etLogin1C.text.toString()
        val password = binding.etPassword1C.text.toString()
        func(host, base, name, login, password)
    }

    private fun observeViewModels() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectOnce(viewModel::setStateValue) {
                updateUI(it)
            }
        }
    }

    private fun updateUI(state: SettingsViewModel.State?) {
        if (state == null) return
        val colorGreen = ContextCompat.getColor(
            requireContext(),
            android.R.color.holo_green_light
        )
        val colorRed = ContextCompat.getColor(
            requireContext(),
            android.R.color.holo_red_light
        )

        onDefaultViews()

        when (state) {
            is SettingsViewModel.EmptySettings -> {
                binding.tilHost1C.error =
                    if (state.host) getString(R.string.error_empty_host) else null
                binding.tilBase1C.error =
                    if (state.base) getString(R.string.error_empty_base) else null
                binding.tilName1C.error =
                    if (state.name) getString(R.string.error_empty_name) else null
            }
            is SettingsViewModel.Error -> {
                if (state.error.isNotBlank()) {
                    Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
                }
                binding.tvStatusTest.text = getString(R.string.status_test_connection_failed)
                binding.tvStatusTest.visibility = View.VISIBLE
                binding.tvStatusTest.setTextColor(colorRed)
                binding.buttonSave.isEnabled = false

            }
            is SettingsViewModel.Progress -> {
                onProgressViews()
            }
            is SettingsViewModel.Saved -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.settings_saved),
                    Toast.LENGTH_SHORT
                ).show()
            }
            is SettingsViewModel.Success -> {
                binding.tvStatusTest.text = getString(R.string.status_test_connection_success)
                binding.tvStatusTest.setTextColor(colorGreen)
                binding.tvStatusTest.visibility = View.VISIBLE
                binding.buttonSave.isEnabled = true
            }
            is SettingsViewModel.SetSettings -> {
                binding.etHost1C.setText(state.host)
                binding.etBase1C.setText(state.base)
                binding.etName1C.setText(state.name)
                binding.etLogin1C.setText(state.login)
                binding.etPassword1C.setText(state.password)
            }
        }
    }

    private fun onDefaultViews() {
        with(binding) {
            progressBar.visibility = View.GONE

            tilHost1C.error = null
            tilBase1C.error = null
            tilName1C.error = null
            tilLogin1C.error = null

            tilHost1C.isEnabled = true
            tilBase1C.isEnabled = true
            tilName1C.isEnabled = true
            tilLogin1C.isEnabled = true
            tilPassword1C.isEnabled = true

            buttonSave.isEnabled = false
            buttonCancel.isEnabled = true
            buttonTestConnection.isEnabled = true

            tvStatusTest.visibility = View.INVISIBLE
        }
    }

    private fun onProgressViews() {
        with(binding) {
            progressBar.visibility = View.VISIBLE
            tvStatusTest.visibility = View.INVISIBLE
            tilHost1C.isEnabled = false
            tilBase1C.isEnabled = false
            tilName1C.isEnabled = false
            tilLogin1C.isEnabled = false
            tilPassword1C.isEnabled = false

            buttonSave.isEnabled = false
            buttonCancel.isEnabled = false
            buttonTestConnection.isEnabled = false
        }
    }

}