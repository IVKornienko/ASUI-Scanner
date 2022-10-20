package com.ivkornienko.asui.scanner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val _state: MutableStateFlow<State?> = MutableStateFlow(null)
    val state: StateFlow<State?>
        get() = _state

    fun setStateValue(state: State?) {
        _state.value = state
    }

    private val repositoryImpl =
        SettingsRepositoryImpl(AppSettingSharedPreferences(getApplication()))

    fun testConnection(url: String, login: String, password: String) {
        _state.value = Progress
        if (checkEmptyFields(url, login)) return

        viewModelScope.launch {
            try {
                delay(3000)
                val settings = ApiSettings(url, login, password)
                val result = repositoryImpl.testConnectionSettings(
                    settings
                )
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
            repositoryImpl.saveConnectionSettings(
                settings
            )
            _state.value = Saved
        }
    }

    fun defaultConnectionSettings() {
        _state.value = Progress
        viewModelScope.launch {
            val (url, login, password) = repositoryImpl.defaultConnectionSettings()
            _state.value = SetSettings(url, login, password)
        }
    }

    fun loadConnectionSettings() {
        _state.value = Progress
        viewModelScope.launch {
            val (url, login, password) = repositoryImpl.loadConnectionSettings()
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
