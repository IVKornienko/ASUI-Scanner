package com.ivkornienko.asui.scanner.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

//    private val storageConnectionSettingsRepository =
//        StorageConnectionSettingsRepositoryImpl(context)
//    private val repository = ConnectionSettingsRepositoryImpl(storageConnectionSettingsRepository)
//
//    private val getConnectionSettingsUseCase = GetConnectionSettingsUseCase(repository)
//    private val getDefaultConnectionSettingsUseCase =
//        GetDefaultConnectionSettingsUseCase(repository)
//    private val setConnectionSettingsUseCase = SetConnectionSettingsUseCase(repository)
//    private val testConnectionUseCase = TestConnectionUseCase(repository)

    fun testConnection(url: String, login: String, password: String) {
        _state.value = Progress
        if (checkEmptyFields(url, login)) return

        viewModelScope.launch {
            try {
                delay(3000)
                val settings = ApiSettings(url, login, password)
                val result = testConnectionUseCase(settings)
                processResultTextField(result)
            } catch (e: Exception) {
                processOtherSystemExceptions(e.message.toString())
            }
        }
    }

    fun saveConnectionSettings(url: String, login: String, password: String) {
        _state.value = Progress
        if (checkEmptyFields(url, login)) return

        viewModelScope.launch {
            delay(1000)
            val settings = ApiSettings(url, login, password)
            setConnectionSettingsUseCase(settings)
            _state.value = Saved
        }
    }

    fun defaultConnectionSettings() {
        _state.value = Progress
        viewModelScope.launch {
            val (url, login, password) = getDefaultConnectionSettingsUseCase()
            _state.value = SetSettings(url, login, password)
        }
    }

    fun loadConnectionSettings() {
        _state.value = Progress
        viewModelScope.launch {
            val (url, login, password) = getConnectionSettingsUseCase()
            _state.value = SetSettings(url, login, password)
        }
    }

    private fun checkEmptyFields(url: String, login: String): Boolean {
        if (url.isBlank()) {
            _state.value = EmptyURL
            return true
        }
        if (login.isBlank()) {
            _state.value = EmptyLogin
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
    object EmptyURL : State()
    object EmptyLogin : State()
    object Progress : State()
    object Success : State()
    class Error(
        val error: String
    ) : State()

    class SetSettings(
        val url: String,
        val login: String,
        val password: String
    ) : State()
}
