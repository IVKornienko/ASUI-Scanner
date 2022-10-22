package com.ivkornienko.asui.scanner.di

import android.app.Application
import com.ivkornienko.asui.scanner.domain.entity.ApiSettings
import com.ivkornienko.asui.scanner.presentation.history.HistoryFragment
import com.ivkornienko.asui.scanner.presentation.productinfo.ProductInfoFragment
import com.ivkornienko.asui.scanner.presentation.scanner.ScannerFragment
import com.ivkornienko.asui.scanner.presentation.settings.SettingsFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(fragment: ScannerFragment)
    fun inject(fragment: HistoryFragment)
    fun inject(fragment: ProductInfoFragment)
    fun inject(fragment: SettingsFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
            @BindsInstance settings: ApiSettings = ApiSettings("http://localhost", "", "")
        ): ApplicationComponent
    }

}