package com.ivkornienko.asui.scanner.presentation.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivkornienko.asui.scanner.domain.HostNotFoundException
import com.ivkornienko.asui.scanner.domain.usecase.productinfo.LoadProductInfoByBarcodeUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScannerViewModel @Inject constructor(
    private val loadProductInfoByBarcodeUseCase: LoadProductInfoByBarcodeUseCase
) : ViewModel() {

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
            } catch (e: HostNotFoundException) {
                _state.value = EmptyHost
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
    object EmptyHost: State()
    object Progress : State()
    class Success(val result: Long) : State()
    class Error(val error: String) : State()
}