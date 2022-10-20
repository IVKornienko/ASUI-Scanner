package com.ivkornienko.asui.scanner

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.ivkornienko.asui.scanner.databinding.FragmentSettingsBinding
import kotlinx.coroutines.launch

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)

        binding.etBaseURL.addTextChangedListener {
            binding.buttonSave.isEnabled = false
            binding.tilBaseURL.error = null
        }
        binding.etLogin1C.addTextChangedListener {
            binding.buttonSave.isEnabled = false
            binding.tilLogin1C.error = null
        }

        createMenu()
        setListeners()
        observeViewModels()

        if (savedInstanceState == null) viewModel.loadConnectionSettings()
    }

    private fun createMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.settings_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.item_defaultSettings -> {
                        viewModel.defaultConnectionSettings()
                        return false
                    }
                }
                return true
            }
        }, viewLifecycleOwner)
    }

    private fun setListeners() {
        binding.buttonTestConnection.setOnClickListener {
            val url = binding.etBaseURL.text.toString()
            val login = binding.etLogin1C.text.toString()
            val password = binding.etPassword1C.text.toString()

            viewModel.testConnection(url, login, password)
        }

        binding.buttonCancel.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

        binding.buttonSave.setOnClickListener {
            val url = binding.etBaseURL.text.toString()
            val login = binding.etLogin1C.text.toString()
            val password = binding.etPassword1C.text.toString()

            viewModel.saveConnectionSettings(url, login, password)
        }
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
            is SettingsViewModel.EmptyLogin -> {
                binding.tilLogin1C.error = "Login is empty"
            }
            is SettingsViewModel.EmptyURL -> {
                binding.tilBaseURL.error = "URL is empty"
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
                binding.progressBar.visibility = View.VISIBLE
                binding.tvStatusTest.visibility = View.INVISIBLE
                binding.tilBaseURL.isEnabled = false
                binding.tilLogin1C.isEnabled = false
                binding.tilPassword1C.isEnabled = false

                binding.buttonSave.isEnabled = false
                binding.buttonCancel.isEnabled = false
                binding.buttonTestConnection.isEnabled = false
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
                binding.etBaseURL.setText(state.url)
                binding.etLogin1C.setText(state.login)
                binding.etPassword1C.setText(state.password)
            }
        }
    }

    private fun onDefaultViews() {
        binding.progressBar.visibility = View.GONE

        binding.tilBaseURL.error = null
        binding.tilLogin1C.error = null

        binding.tilBaseURL.isEnabled = true
        binding.tilLogin1C.isEnabled = true
        binding.tilPassword1C.isEnabled = true

        binding.buttonSave.isEnabled = false
        binding.buttonCancel.isEnabled = true
        binding.buttonTestConnection.isEnabled = true

        binding.tvStatusTest.visibility = View.INVISIBLE
    }
}