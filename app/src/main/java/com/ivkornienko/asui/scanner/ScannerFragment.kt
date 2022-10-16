package com.ivkornienko.asui.scanner

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ivkornienko.asui.scanner.databinding.FragmentScannerBinding
import com.journeyapps.barcodescanner.ScanOptions

class ScannerFragment : Fragment(R.layout.fragment_scanner) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentScannerBinding.bind(view)
        binding.buttonGetFromScanner.setOnClickListener{
            val options = ScanOptions()

        }
    }
}