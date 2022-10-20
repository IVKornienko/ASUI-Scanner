package com.ivkornienko.asui.scanner.presentation.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ivkornienko.asui.scanner.data.ProductInfoMapper
import com.ivkornienko.asui.scanner.data.database.AppDatabase
import com.ivkornienko.asui.scanner.data.repository.ProductInfoRepositoryImpl
import com.ivkornienko.asui.scanner.data.repository.StorageConnectionSettingsRepositoryImpl
import com.ivkornienko.asui.scanner.domain.usecase.productinfo.ClearHistoryUseCase
import com.ivkornienko.asui.scanner.domain.usecase.productinfo.GetProductInfoListUseCase
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val productInfoDao = AppDatabase.getInstance(application).productInfoDao()
    private val productInfoMapper = ProductInfoMapper()
    private val storageConnectionSettingsRepository =
        StorageConnectionSettingsRepositoryImpl(application)

    private val repository = ProductInfoRepositoryImpl(
        productInfoDao,
        productInfoMapper,
        storageConnectionSettingsRepository
    )

    private val clearHistoryUseCase = ClearHistoryUseCase(repository)
    private val getProductInfoListUseCase = GetProductInfoListUseCase(repository)

    val history = getProductInfoListUseCase()

    fun clearHistory() {
        viewModelScope.launch { clearHistoryUseCase() }
    }
}