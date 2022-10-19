package com.ivkornienko.asui.scanner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ScannerViewModel(application: Application) : AndroidViewModel(application) {

    private val _state = MutableLiveData<State>()
    val state = _state.share()

    fun getInfoByBarcode(barcode: String) {
        _state.value = Progress
        if (barcode.isBlank()) {
            _state.value = EmptyBarcode
            return
        }

        viewModelScope.launch {
            try {
                delay(1000)
                val result = ProductInfoRepositoryImpl(AppSettingSharedPreferences(getApplication())).getInfoByBarcode(barcode)
                processResult(result)
            } catch (e: Exception) {
                processOtherSystemExceptions(e.message.toString())
            }
        }
    }

    fun clear_state() {
        _state.value = Empty
    }

    private fun processResult(result: ProductInfo) {
        _state.value = if (result.error.isBlank()) Success(result) else Error(result.error)
    }

    private fun processOtherSystemExceptions(errorMessage: String) {
        _state.value = Error(errorMessage)
    }

    sealed class State
    object Empty: State()
    object EmptyBarcode : State()
    object Progress : State()
    class Success(val result: ProductInfo) : State()
    class Error(val error: String) : State()

}