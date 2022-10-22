package com.ivkornienko.asui.scanner.di

import androidx.lifecycle.ViewModel
import com.ivkornienko.asui.scanner.presentation.history.HistoryViewModel
import com.ivkornienko.asui.scanner.presentation.productinfo.ProductInfoViewModel
import com.ivkornienko.asui.scanner.presentation.scanner.ScannerViewModel
import com.ivkornienko.asui.scanner.presentation.settings.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(ScannerViewModel::class)
    @Binds
    fun bindScannerViewModel(impl: ScannerViewModel): ViewModel

    @IntoMap
    @ViewModelKey(HistoryViewModel::class)
    @Binds
    fun bindHistoryViewModel(impl: HistoryViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ProductInfoViewModel::class)
    @Binds
    fun bindProductInfoViewModel(impl: ProductInfoViewModel): ViewModel

    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    @Binds
    fun bindSettingsViewModel(impl: SettingsViewModel): ViewModel

}