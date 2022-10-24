package com.ivkornienko.asui.scanner.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivkornienko.asui.scanner.domain.HostNotFoundException
import com.ivkornienko.asui.scanner.domain.entity.ApiSettings
import com.ivkornienko.asui.scanner.domain.usecase.connectionsettings.GetConnectionSettingsUseCase
import com.ivkornienko.asui.scanner.domain.usecase.connectionsettings.GetDefaultConnectionSettingsUseCase
import com.ivkornienko.asui.scanner.domain.usecase.connectionsettings.SetConnectionSettingsUseCase
import com.ivkornienko.asui.scanner.domain.usecase.connectionsettings.TestConnectionUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val getConnectionSettingsUseCase: GetConnectionSettingsUseCase,
    private val getDefaultConnectionSettingsUseCase: GetDefaultConnectionSettingsUseCase,
    private val setConnectionSettingsUseCase: SetConnectionSettingsUseCase,
    private val testConnectionUseCase: TestConnectionUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<State?> = MutableStateFlow(null)
    val state: StateFlow<State?>
        get() = _state

    fun setStateValue(state: State?) {
        _state.value = state
    }

    fun testConnection(host: String, login: String, password: String) {
        _state.value = Progress
        if (validateFields(host)) return

        viewModelScope.launch {
            try {
                delay(3000)
                val settings = ApiSettings(host, login, password)
                val result = testConnectionUseCase(settings)
                processResultTextField(result)
            } catch (e: HostNotFoundException) {
                _state.value = EmptyHost
            } catch (e: Exception) {
                processOtherSystemExceptions(e.message.toString())
            }
        }
    }

    fun saveConnectionSettings(host: String, login: String, password: String) {
        _state.value = Progress
        if (validateFields(host)) return

        viewModelScope.launch {
            delay(1000)
            val settings = ApiSettings(host, login, password)
            setConnectionSettingsUseCase(settings)
            _state.value = Saved
        }
    }

    fun defaultConnectionSettings() {
        _state.value = Progress
        viewModelScope.launch {
            val (host, login, password) = getDefaultConnectionSettingsUseCase()
            _state.value = SetSettings(host, login, password)
        }
    }

    fun loadConnectionSettings() {
        _state.value = Progress
        viewModelScope.launch {
            val (host, login, password) = getConnectionSettingsUseCase()
            _state.value = SetSettings(host, login, password)
        }
    }

    private fun validateFields(host: String): Boolean {
        if (host.isBlank()) {
            _state.value = EmptyHost
            return true
        }
        return false
    }

    private fun processResultTextField(result: Boolean) {
        _state.value = if (result) Success else Error("")
    }

    private fun processOtherSystemExceptions(errorMessage: String) {
        _state.value = Error(errorMessage)
    }

    sealed class State
    object Saved : State()
    object EmptyHost : State()
    object Progress : State()
    object Success : State()
    class Error(
        val error: String
    ) : State()

    class SetSettings(
        val host: String,
        val login: String,
        val password: String
    ) : State()
}
