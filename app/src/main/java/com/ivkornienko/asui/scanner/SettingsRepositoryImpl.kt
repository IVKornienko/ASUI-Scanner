package com.ivkornienko.asui.scanner

import android.content.Context
import com.ivkornienko.asui.scanner.ApiSettings.Companion.DEFAULT_LOGIN_SERVICE_1C
import com.ivkornienko.asui.scanner.ApiSettings.Companion.DEFAULT_PASSWORD_SERVICE_1C
import com.ivkornienko.asui.scanner.ApiSettings.Companion.DEFAULT_URL_SERVICE_1C

class SettingsRepositoryImpl(
    private val context: Context
) : SettingsRepository {

    override suspend fun testConnectionSettings(settings: ApiSettings): Boolean {
        val apiService = ApiFactory(context, settings).apiService
        val response = apiService.test_connection(TEST_STRING)
        return response.test_string == TEST_STRING
    }

    override suspend fun loadConnectionSettings(): ApiSettings {
        val apiSettings = AppSettingSharedPreferences(context)

        return ApiSettings(
            apiSettings.getURLService1C().ifBlank { DEFAULT_URL_SERVICE_1C },
            apiSettings.getLoginService1C().ifBlank { DEFAULT_LOGIN_SERVICE_1C },
            apiSettings.getPasswordService1C().ifBlank { DEFAULT_PASSWORD_SERVICE_1C },
        )
    }

    override suspend fun saveConnectionSettings(settings: ApiSettings) {
        AppSettingSharedPreferences(context).setApiSettings(settings)
    }

    override suspend fun clearConnectionSettings() {
        AppSettingSharedPreferences(context).clearApiSettings()
    }

    companion object {
        private const val TEST_STRING = "test string"
    }
}