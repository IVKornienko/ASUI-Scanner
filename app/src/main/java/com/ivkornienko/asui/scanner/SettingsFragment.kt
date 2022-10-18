package com.ivkornienko.asui.scanner

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.ivkornienko.asui.scanner.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)

        createMenu()
        setListeners()
        observeViewModels()
    }

    private fun createMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.settings_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.item_defaultSettings -> {
                        viewModel.clearConnectionSettings()
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

        viewModel.state.observe(viewLifecycleOwner) {
            val colorGreen = ContextCompat.getColor(
                requireContext(),
                android.R.color.holo_green_light
            )
            val colorRed = ContextCompat.getColor(
                requireContext(),
                android.R.color.holo_red_light
            )

            onDefaultViews()
            when (it) {
                is SettingsViewModel.EmptyURL -> {
                    binding.tilBaseURL.error = "URL is empty"
                }
                is SettingsViewModel.EmptyLogin -> {
                    binding.tilLogin1C.error = "Login is empty"
                }
                is SettingsViewModel.EmptyPassword -> {
                    binding.tilPassword1C.error = "Password is empty"
                }
                is SettingsViewModel.Error -> {
                    if (it.error.isNotBlank()) {
                        Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
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
                is SettingsViewModel.Success -> {
                    binding.tvStatusTest.text = getString(R.string.status_test_connection_success)
                    binding.tvStatusTest.setTextColor(colorGreen)
                    binding.tvStatusTest.visibility = View.VISIBLE
                    binding.buttonSave.isEnabled = true
                }
                SettingsViewModel.Saved -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.settings_saved),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewModel.getSettings.observeEvent(viewLifecycleOwner) {
            binding.etBaseURL.setText(it.url)
            binding.etLogin1C.setText(it.login)
            binding.etPassword1C.setText(it.password)
        }
    }

    private fun onDefaultViews() {
        binding.progressBar.visibility = View.GONE

        binding.tilBaseURL.error = null
        binding.tilLogin1C.error = null
        binding.tilPassword1C.error = null

        binding.tilBaseURL.isEnabled = true
        binding.tilLogin1C.isEnabled = true
        binding.tilPassword1C.isEnabled = true

        binding.buttonSave.isEnabled = true
        binding.buttonCancel.isEnabled = true
        binding.buttonTestConnection.isEnabled = true

        binding.tvStatusTest.visibility = View.INVISIBLE
    }

    override fun onPause() {
        super.onPause()
        viewModel.clearError()
    }
}