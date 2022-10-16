package com.ivkornienko.asui.scanner

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.zxing.client.android.Intents
import com.ivkornienko.asui.scanner.databinding.FragmentScannerBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class ScannerFragment : Fragment(R.layout.fragment_scanner) {

    private lateinit var binding: FragmentScannerBinding

    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            val originalIntent = result.originalIntent
            if (originalIntent == null) {
                Toast.makeText(context, getString(R.string.scanning_cancelled), Toast.LENGTH_LONG).show()
            } else if (originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION)) {
                Toast.makeText(
                    context,
                    getString(R.string.scanning_not_permissions),
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            binding.textInputEditTextBarcode.setText(result.contents)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentScannerBinding.bind(view)
        binding.buttonGetFromScanner.setOnClickListener {
            val options = ScanOptions().apply {
                setOrientationLocked(false)
                captureActivity = ScanningActivity::class.java
            }
            barcodeLauncher.launch(options)
        }
    }
}