package com.ivkornienko.asui.scanner.presentation.productinfo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.ivkornienko.asui.scanner.AsuiScannerApplication
import com.ivkornienko.asui.scanner.R
import com.ivkornienko.asui.scanner.databinding.FragmentProductInfoBinding
import com.ivkornienko.asui.scanner.domain.entity.ProductInfo
import com.ivkornienko.asui.scanner.presentation.ViewModelFactory
import javax.inject.Inject

class ProductInfoFragment : Fragment(R.layout.fragment_product_info) {

    private var _binding: FragmentProductInfoBinding? = null
    private val binding: FragmentProductInfoBinding
        get() = _binding ?: throw RuntimeException("FragmentProductInfoBinding is Nullable")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ProductInfoViewModel::class.java]
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
        _binding = FragmentProductInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: ProductInfoFragmentArgs by navArgs()
        val productInfoId = args.productInfoId

        viewModel.getProductItem(productInfoId).observe(viewLifecycleOwner) {
            updateUI(it)
        }
    }

    private fun updateUI(productInfo: ProductInfo) {
        with(binding) {
            tvFullName.text = productInfo.fullName
            tvInvNumber.text = productInfo.invNumber
            tvBarcode.text = productInfo.barcode
            tvSerialNumber.text = productInfo.serialNumber
            tvTypeAsset.text = productInfo.typeAsset
            tvCount.text = productInfo.count.toString()
            tvOperationDate.text = productInfo.operationDate
            tvOrganization.text = productInfo.organizationName
            tvStore.text = productInfo.storeName
            tvCurrentState.text = productInfo.state
            tvInventDate.text = productInfo.inventDate
            tvExist.text = productInfo.exist
            tvInAsui.text =
                if (productInfo.inAsui) getString(R.string.text_true) else getString(R.string.text_false)
            tvDateTimeScan.text = productInfo.timeScan
        }
    }
}