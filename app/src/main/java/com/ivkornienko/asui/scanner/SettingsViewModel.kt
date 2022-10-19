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

    fun testConnection(url: String, login: String, password: String) {
        _state.value = Progress
        checkEmptyFields(url, login)
        viewModelScope.launch {
            try {
                delay(3000)
                val settings = ApiSettings(url, login, password)
                val result =
                    SettingsRepositoryImpl(AppSettingSharedPreferences(getApplication())).testConnectionSettings(
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
        checkEmptyFields(url, login)
        viewModelScope.launch {
            delay(1000)
            val settings = ApiSettings(url, login, password)
            SettingsRepositoryImpl(AppSettingSharedPreferences(getApplication())).saveConnectionSettings(
                settings
            )
            _state.value = Saved
        }
    }

    fun clearConnectionSettings() {
        viewModelScope.launch {
            SettingsRepositoryImpl(AppSettingSharedPreferences(getApplication())).clearConnectionSettings()
            loadConnectionSettings()
        }
    }

    fun clear_state() {
        _state.value = Empty
    }

    fun loadConnectionSettings() {
        viewModelScope.launch {
            _getSettings.publishEvent(
                SettingsRepositoryImpl(AppSettingSharedPreferences(getApplication())).loadConnectionSettings()
            )
        }
    }

    private fun checkEmptyFields(url: String, login: String) {
        if (url.isBlank()) {
            _state.value = EmptyURL
            return
        }
        if (login.isBlank()) {
            _state.value = EmptyLogin
            return
        }
    }

    private fun processResultTextField(result: Boolean) {
        _state.value = if (result) Success else Error("")
    }

    private fun processOtherSystemExceptions(errorMessage: String) {
        _state.value = Error(errorMessage)
    }

    sealed class State
    object Empty : State()
    object Saved : State()
    object EmptyURL : State()
    object EmptyLogin : State()
    object Progress : State()
    object Success : State()
    class Error(
        var error: String
    ) : State()
}
