package com.ivkornienko.asui.scanner

import android.content.pm.PackageManager
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ivkornienko.asui.scanner.databinding.ActivityScanningBinding
import com.journeyapps.barcodescanner.CaptureActivity
import com.journeyapps.barcodescanner.DecoratedBarcodeView

class ScanningActivity : CaptureActivity() {
    private lateinit var binding: ActivityScanningBinding
    override fun initializeContent(): DecoratedBarcodeView {
        binding = ActivityScanningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!hasFlash()) {
            binding.switchFlashlight.visibility = View.GONE
        }

        return binding.zxingBarcodeScanner
    }

    private fun hasFlash(): Boolean {
        return applicationContext.packageManager
            .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    fun switchFlashlight(view: View) {
        if ((view as FloatingActionButton).tag.equals("off")) {
            onTorchOn()
        } else {
            onTorchOff()
        }
    }

    private fun onTorchOn() {
        binding.switchFlashlight.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_flashlight_off))
        binding.switchFlashlight.tag = "on"
        binding.zxingBarcodeScanner.setTorchOn()
    }

    private fun onTorchOff() {
        binding.switchFlashlight.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_flashlight_on))
        binding.switchFlashlight.tag = "off"
        binding.zxingBarcodeScanner.setTorchOff()
    }

}