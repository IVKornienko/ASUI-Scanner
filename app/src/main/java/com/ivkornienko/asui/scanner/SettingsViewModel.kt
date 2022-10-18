package com.ivkornienko.asui.scanner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val _state = MutableLiveData<State>()
    val state = _state.share()

    private val _getSettings = MutableLiveData<Event<ApiSettings>>()
    val getSettings = _getSettings.share()

    init {
        loadConnectionSettings()
    }

    fun testConnection(url: String, login: String, password: String) {
        _state.value = Progress
        checkEmptyFields(url, login, password)
        viewModelScope.launch {
            try {
                delay(3000)
                val settings = ApiSettings(url, login, password)
                val result =
                    SettingsRepositoryImpl(getApplication()).testConnectionSettings(settings)
                _state.value = if (result) Success else Error("")
                processResultTextField(result)
            } catch (e: Exception) {
                processOtherSystemExceptions(e.message.toString())
            }
        }
    }

    fun saveConnectionSettings(url: String, login: String, password: String) {
        _state.value = Progress
        checkEmptyFields(url, login, password)
        viewModelScope.launch {
            delay(1000)
            val settings = ApiSettings(url, login, password)
            SettingsRepositoryImpl(getApplication()).saveConnectionSettings(settings)
            _state.value = Saved
        }
    }

    fun clearConnectionSettings() {
        viewModelScope.launch {
            SettingsRepositoryImpl(getApplication()).clearConnectionSettings()
            loadConnectionSettings()
        }
    }

    fun clearError() {
        if (_state.value is Error) {
            processOtherSystemExceptions("")
        }
    }

    private fun checkEmptyFields(url: String, login: String, password: String) {
        if (url.isBlank()) {
            _state.value = EmptyURL
            return
        }
        if (login.isBlank()) {
            _state.value = EmptyLogin
            return
        }
        if (password.isBlank()) {
            _state.value = EmptyPassword
            return
        }
    }

    private fun loadConnectionSettings() {
        viewModelScope.launch {
            _getSettings.publishEvent(
                SettingsRepositoryImpl(getApplication()).loadConnectionSettings()
            )
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
    object EmptyURL : State()
    object EmptyLogin : State()
    object EmptyPassword : State()
    object Progress : State()
    object Success : State()
    class Error(
        var error: String
    ) : State()
}
