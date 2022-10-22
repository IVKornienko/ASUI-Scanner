package com.ivkornienko.asui.scanner

import android.app.Application
import com.ivkornienko.asui.scanner.di.DaggerApplicationComponent

class AsuiScannerApplication: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

}