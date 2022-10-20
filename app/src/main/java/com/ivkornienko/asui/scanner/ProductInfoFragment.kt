package com.ivkornienko.asui.scanner

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ivkornienko.asui.scanner.databinding.FragmentProductInfoBinding

class ProductInfoFragment : Fragment(R.layout.fragment_product_info) {

    private lateinit var binding: FragmentProductInfoBinding
    val viewModel: ProductInfoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProductInfoBinding.bind(view)

        val args: ProductInfoFragmentArgs by navArgs()
        val productInfoId = args.productInfoId

        viewModel.getProductItem(productInfoId)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.productInfo.observe(viewLifecycleOwner) {
            with(binding) {
                tvFullName.text = it.fullName
                tvInvNumber.text = it.invNumber
                tvBarcode.text = it.barcode
                tvSerialNumber.text = it.serialNumber
                tvTypeAsset.text = it.typeAsset
                tvCount.text = it.count.toString()
                tvOperationDate.text = it.operationDate
                tvOrganization.text = it.organizationName
                tvStore.text = it.storeName
                tvCurrentState.text = it.state
                tvInventDate.text = it.inventDate
                tvExist.text = it.exist
                tvInAsui.text = if (it.inAsui) "Да" else "Нет"
                tvDateTimeScan.text = it.timeScan
            }
        }
    }
}