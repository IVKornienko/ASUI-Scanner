package com.ivkornienko.asui.scanner.presentation.scanner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ivkornienko.asui.scanner.data.ProductInfoMapper
import com.ivkornienko.asui.scanner.data.database.AppDatabase
import com.ivkornienko.asui.scanner.data.repository.ProductInfoRepositoryImpl
import com.ivkornienko.asui.scanner.data.repository.StorageConnectionSettingsRepositoryImpl
import com.ivkornienko.asui.scanner.domain.usecase.productinfo.LoadProductInfoByBarcodeUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ScannerViewModel(application: Application) : AndroidViewModel(application) {

    private val productInfoDao = AppDatabase.getInstance(application).productInfoDao()
    private val productInfoMapper = ProductInfoMapper()
    private val storageConnectionSettingsRepository =
        StorageConnectionSettingsRepositoryImpl(application)

    private val repository = ProductInfoRepositoryImpl(
        productInfoDao,
        productInfoMapper,
        storageConnectionSettingsRepository
    )

    private val loadProductInfoByBarcodeUseCase = LoadProductInfoByBarcodeUseCase(repository)

    private val _state: MutableStateFlow<State?> = MutableStateFlow(null)
    val state: StateFlow<State?>
        get() = _state

    fun setStateValue(state: State?) {
        _state.value = state
    }

    fun getInfoByBarcode(barcode: String) {
        _state.value = Progress
        if (barcode.isBlank()) {
            _state.value = EmptyBarcode
            return
        }

        viewModelScope.launch {
            try {
                delay(1000)
                val result = loadProductInfoByBarcodeUseCase(barcode)
                processResult(result)
            } catch (e: Exception) {
                processOtherSystemExceptions(e.message.toString())
            }
        }
    }

    private fun processResult(result: Long) {
        _state.value = Success(result)
    }

    private fun processOtherSystemExceptions(errorMessage: String) {
        _state.value = Error(errorMessage)
    }

    sealed class State
    object EmptyBarcode : State()
    object Progress : State()
    class Success(val result: Long) : State()
    class Error(val error: String) : State()
}