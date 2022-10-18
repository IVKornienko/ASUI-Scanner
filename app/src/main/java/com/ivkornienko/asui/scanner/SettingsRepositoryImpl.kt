package com.ivkornienko.asui.scanner

import android.content.Context

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
            apiSettings.getURLService1C(),
            apiSettings.getLoginService1C(),
            apiSettings.getPasswordService1C()
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