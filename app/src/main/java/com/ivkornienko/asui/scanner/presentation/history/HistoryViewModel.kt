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

    val productInfoList = getProductInfoListUseCase()

    fun clearHistory() {
        viewModelScope.launch { clearHistoryUseCase() }
    }
}