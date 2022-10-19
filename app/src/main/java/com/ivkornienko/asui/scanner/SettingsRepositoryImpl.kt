package com.ivkornienko.asui.scanner

import com.ivkornienko.asui.scanner.ApiSettings.Companion.DEFAULT_LOGIN_SERVICE_1C
import com.ivkornienko.asui.scanner.ApiSettings.Companion.DEFAULT_PASSWORD_SERVICE_1C
import com.ivkornienko.asui.scanner.ApiSettings.Companion.DEFAULT_URL_SERVICE_1C
import com.ivkornienko.asui.scanner.network.ApiFactory

class SettingsRepositoryImpl(
    private val appSettings: AppSettings
) : SettingsRepository {

    override suspend fun testConnectionSettings(settings: ApiSettings): Boolean {
        val apiService = ApiFactory(settings).apiService
        val response = apiService.test_connection(TEST_STRING)
        return response.test_string == TEST_STRING
    }

    override suspend fun loadConnectionSettings(): ApiSettings {
        return ApiSettings(
            appSettings.getURLService1C().ifBlank { DEFAULT_URL_SERVICE_1C },
            appSettings.getLoginService1C().ifBlank { DEFAULT_LOGIN_SERVICE_1C },
            appSettings.getPasswordService1C().ifBlank { DEFAULT_PASSWORD_SERVICE_1C },
        )
    }

    override suspend fun saveConnectionSettings(settings: ApiSettings) {
        appSettings.setApiSettings(settings)
    }

    override suspend fun clearConnectionSettings() {
        appSettings.clearApiSettings()
    }

    companion object {
        private const val TEST_STRING = "test string"
    }
}