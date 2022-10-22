package com.ivkornienko.asui.scanner.data.repository

import android.app.Application
import android.content.Context
import com.ivkornienko.asui.scanner.domain.entity.ApiSettings
import com.ivkornienko.asui.scanner.domain.repository.StorageConnectionSettingsRepository
import javax.inject.Inject

class StorageConnectionSettingsRepositoryImpl @Inject constructor(
    application: Application
) : StorageConnectionSettingsRepository {

    private val sharedPreferences =
        application.getSharedPreferences(
            PREFERENCES_NAME,
            Context.MODE_PRIVATE,
        )

    override fun saveConnectionSettings(settings: ApiSettings) {
        setPreference(PREFERENCE_HOST_SERVICE1C, settings.host)
        setPreference(PREFERENCE_LOGIN_SERVICE1C, settings.login)
        setPreference(PREFERENCE_PASSWORD_SERVICE1C, settings.password)
    }

    override fun readConnectionSettings(): ApiSettings {
        return ApiSettings(
            host = sharedPreferences.getString(PREFERENCE_HOST_SERVICE1C, "") ?: "",
            login = sharedPreferences.getString(PREFERENCE_LOGIN_SERVICE1C, "") ?: "",
            password = sharedPreferences.getString(PREFERENCE_PASSWORD_SERVICE1C, "") ?: "",
        )
    }

    override fun resetConnectionSettings() {
        setPreference(PREFERENCE_HOST_SERVICE1C, null)
        setPreference(PREFERENCE_LOGIN_SERVICE1C, null)
        setPreference(PREFERENCE_PASSWORD_SERVICE1C, null)
    }

    private fun setPreference(name: String, value: String?) {
        val editor = sharedPreferences.edit()
        if (value == null) editor.remove(name)
        else editor.putString(name, value)
        editor.apply()
    }

    companion object {
        private const val PREFERENCES_NAME = "settings_Service1C"

        private const val PREFERENCE_HOST_SERVICE1C = "host"
        private const val PREFERENCE_LOGIN_SERVICE1C = "login"
        private const val PREFERENCE_PASSWORD_SERVICE1C = "password"
    }
}