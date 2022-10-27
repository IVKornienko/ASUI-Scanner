package com.ivkornienko.asui.scanner.presentation.history

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ivkornienko.asui.scanner.AsuiScannerApplication
import com.ivkornienko.asui.scanner.R
import com.ivkornienko.asui.scanner.databinding.FragmentHistoryBinding
import com.ivkornienko.asui.scanner.presentation.ViewModelFactory
import javax.inject.Inject


class HistoryFragment : Fragment(R.layout.fragment_history) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[HistoryViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as AsuiScannerApplication).component
    }

    private var _binding: FragmentHistoryBinding? = null
    private val binding: FragmentHistoryBinding
        get() = _binding ?: throw RuntimeException("FragmentHistoryBinding is Nullable")


    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createMenu()
        setupRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.history_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.item_clearHistory -> {
                        viewModel.clearHistory()
                        return false
                    }
                }
                return true
            }
        }, viewLifecycleOwner)
    }

    private fun setupRecyclerView() {
        val adapter = HistoryAdapter()

        binding.rvProductItemList.adapter = adapter
        adapter.onHistoryItemClickListener = {
            val direction =
                HistoryFragmentDirections.histryFragmentToInfoByBarcodeFragment(
                    it.id
                )
            findNavController().navigate(direction)
        }

        viewModel.productInfoList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

    }
}