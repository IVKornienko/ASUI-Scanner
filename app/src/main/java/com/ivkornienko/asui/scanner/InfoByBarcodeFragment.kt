package com.ivkornienko.asui.scanner

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ivkornienko.asui.scanner.databinding.FragmentInfoByBarcodeBinding

class InfoByBarcodeFragment : Fragment(R.layout.fragment_info_by_barcode) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentInfoByBarcodeBinding.bind(view)

        val args: InfoByBarcodeFragmentArgs by navArgs()
        val barcode = args.barcode

        with(binding) {
            tvFullName.text = barcode.fullName
            tvInvNumber.text = barcode.invNumber
            tvBarcode.text = barcode.barcode
            tvSerialNumber.text = barcode.serialNumber
            tvTypeAsset.text = barcode.typeAsset
            tvCount.text = barcode.count.toString()
            tvOperationDate.text = barcode.operationDate
            tvOrganization.text = barcode.organizationName
            tvStore.text = barcode.storeName
            tvCurrentState.text = barcode.state
            tvInventDate.text = barcode.inventDate
            tvExist.text = barcode.exist
            tvInAsui.text = if (barcode.inAsui) "Да" else "Нет"
        }
    }

}