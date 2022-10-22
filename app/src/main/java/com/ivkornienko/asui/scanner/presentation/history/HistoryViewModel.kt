package com.ivkornienko.asui.scanner.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivkornienko.asui.scanner.domain.usecase.productinfo.ClearHistoryUseCase
import com.ivkornienko.asui.scanner.domain.usecase.productinfo.GetProductInfoListUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class HistoryViewModel @Inject constructor(
    private val clearHistoryUseCase: ClearHistoryUseCase,
    getProductInfoListUseCase: GetProductInfoListUseCase

) : ViewModel() {

    /*    private val productInfoDao = AppDatabase.getInstance(context).productInfoDao()
        private val productInfoMapper = ProductInfoMapper()
        private val storageConnectionSettingsRepository =
            StorageConnectionSettingsRepositoryImpl(context)

        private val repository = ProductInfoRepositoryImpl(
            productInfoDao,
            productInfoMapper,
            storageConnectionSettingsRepository
        )

        private val clearHistoryUseCase = ClearHistoryUseCase(repository)
        private val getProductInfoListUseCase = GetProductInfoListUseCase(repository)
    */
    val productInfoList = getProductInfoListUseCase()

    fun clearHistory() {
        viewModelScope.launch { clearHistoryUseCase() }
    }
}