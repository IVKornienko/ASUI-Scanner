package com.ivkornienko.asui.scanner.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivkornienko.asui.scanner.domain.EmptyConnectionException
import com.ivkornienko.asui.scanner.domain.entity.ApiSettings
import com.ivkornienko.asui.scanner.domain.usecase.connectionsettings.GetConnectionSettingsUseCase
import com.ivkornienko.asui.scanner.domain.usecase.connectionsettings.GetDefaultConnectionSettingsUseCase
import com.ivkornienko.asui.scanner.domain.usecase.connectionsettings.SetConnectionSettingsUseCase
import com.ivkornienko.asui.scanner.domain.usecase.connectionsettings.TestConnectionUseCase
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

    fun testSettings(host: String, base: String, name: String, login: String, password: String) {
        _state.value = Progress

        viewModelScope.launch {
            try {
                val settings = ApiSettings(host, base, name, login, password)
                val result = testConnectionUseCase(settings)
                processResultTextField(result)
            } catch (e: EmptyConnectionException) {
                _state.value = EmptySettings(
                    host = host.isBlank(),
                    base = base.isBlank(),
                    name = name.isBlank()
                )
            } catch (e: Exception) {
                processOtherSystemExceptions(e.message.toString())
            }
        }
    }

    fun saveSettings(
        host: String, base: String, name: String, login: String, password: String
    ) {
        _state.value = Progress

        viewModelScope.launch {
            val settings = ApiSettings(host, base, name, login, password)
            setConnectionSettingsUseCase(settings)
            _state.value = Saved
        }
    }

    fun defaultSettings() {
        _state.value = Progress
        viewModelScope.launch {
            val (host, base, name, login, password) = getDefaultConnectionSettingsUseCase()
            _state.value = SetSettings(host, base, name, login, password)
        }
    }

    fun loadSettings() {
        _state.value = Progress
        viewModelScope.launch {
            val (host, base, name, login, password) = getConnectionSettingsUseCase()
            _state.value = SetSettings(host, base, name, login, password)
        }
    }

    private fun processResultTextField(result: Boolean) {
        _state.value = if (result) Success else Error("")
    }

    private fun processOtherSystemExceptions(errorMessage: String) {
        _state.value = Error(errorMessage)
    }

    sealed class State
    object Saved : State()
    class EmptySettings(
        var host: Boolean,
        val base: Boolean,
        val name: Boolean
    ) : State()

    object Progress : State()
    object Success : State()
    class Error(
        val error: String
    ) : State()

    class SetSettings(
        val host: String,
        val base: String,
        val name: String,
        val login: String,
        val password: String
    ) : State()
}
