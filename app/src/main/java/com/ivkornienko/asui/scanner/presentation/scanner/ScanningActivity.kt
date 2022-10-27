package com.ivkornienko.asui.scanner.presentation.scanner

import android.content.pm.PackageManager
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ivkornienko.asui.scanner.R
import com.ivkornienko.asui.scanner.databinding.ActivityScanningBinding
import com.journeyapps.barcodescanner.CaptureActivity
import com.journeyapps.barcodescanner.DecoratedBarcodeView

class ScanningActivity : CaptureActivity() {
    private lateinit var binding: ActivityScanningBinding

    override fun initializeContent(): DecoratedBarcodeView {
        binding = ActivityScanningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repaintFlashLight(R.drawable.ic_flashlight_on, android.R.color.holo_orange_light)

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
        if ((view as FloatingActionButton).tag.equals("false")) {
            onTorchOn()
        } else {
            onTorchOff()
        }
    }

    private fun onTorchOn() {
        repaintFlashLight(R.drawable.ic_flashlight_off, R.color.black)
        binding.switchFlashlight.tag = "true"
        binding.zxingBarcodeScanner.setTorchOn()
    }

    private fun onTorchOff() {
        repaintFlashLight(R.drawable.ic_flashlight_on, android.R.color.holo_orange_light)
        binding.switchFlashlight.tag = "false"
        binding.zxingBarcodeScanner.setTorchOff()
    }

    private fun repaintFlashLight(drawable: Int, color: Int) {
        with(binding.switchFlashlight) {
            this.setImageDrawable(
                ContextCompat.getDrawable(
                    this@ScanningActivity,
                    drawable
                )
            )
            this.setColorFilter(
                ContextCompat.getColor(
                    this@ScanningActivity,
                    color
                )
            )
        }
    }
}