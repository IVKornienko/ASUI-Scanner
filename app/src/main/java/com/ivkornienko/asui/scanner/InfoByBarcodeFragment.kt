package com.ivkornienko.asui.scanner

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ivkornienko.asui.scanner.databinding.FragmentInfoByBarcodeBinding


class InfoByBarcodeFragment : Fragment(R.layout.fragment_info_by_barcode) {

    private lateinit var binding: FragmentInfoByBarcodeBinding;

    val args: InfoByBarcodeFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentInfoByBarcodeBinding.bind(view)


        Log.d("Test", args.barcode.toString())
//        Toast.makeText(context, args.barcode.barcode
//            args.barcode.barcode, Toast.LENGTH_SHORT).show()

    }

}