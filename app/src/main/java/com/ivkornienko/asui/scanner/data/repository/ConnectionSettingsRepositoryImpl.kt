package com.ivkornienko.asui.scanner.data.repository

import com.ivkornienko.asui.scanner.data.network.ApiFactory
import com.ivkornienko.asui.scanner.data.network.ApiInterceptor
import com.ivkornienko.asui.scanner.domain.entity.ApiSettings
import com.ivkornienko.asui.scanner.domain.repository.ConnectionSettingsRepository
import com.ivkornienko.asui.scanner.domain.usecase.connectionsettings.ReadConnectionSettingsUseCase
import com.ivkornienko.asui.scanner.domain.usecase.connectionsettings.ResetConnectionSettingsUseCase
import com.ivkornienko.asui.scanner.domain.usecase.connectionsettings.SaveConnectionSettingsUseCase
import retrofit2.Retrofit
import javax.inject.Inject

class ConnectionSettingsRepositoryImpl @Inject constructor(
    private val saveConnectionSettingsUseCase: SaveConnectionSettingsUseCase,
    private val readConnectionSettingsUseCase: ReadConnectionSettingsUseCase,
    private val resetConnectionSettingsUseCase: ResetConnectionSettingsUseCase,
    private val apiInterceptor: ApiInterceptor,
    private val retrofitBuilder: Retrofit.Builder
) : ConnectionSettingsRepository {

    override suspend fun testConnection(settings: ApiSettings): Boolean {
        apiInterceptor.setInterceptor(settings)
        val apiService = ApiFactory(retrofitBuilder, settings.url).apiService
        val response = apiService.testConnection(TEST_STRING)
        return response.test_string == TEST_STRING
    }

    override fun getConnectionSettings(): ApiSettings {
        val url =
            readConnectionSettingsUseCase().url.ifBlank {
                ApiSettings.DEFAULT_URL_SERVICE_1C
            }
        val login =
            readConnectionSettingsUseCase().login.ifBlank {
                ApiSettings.DEFAULT_LOGIN_SERVICE_1C
            }
        val password =
            readConnectionSettingsUseCase().password.ifBlank {
                ApiSettings.DEFAULT_PASSWORD_SERVICE_1C
            }
        return ApiSettings(
            url,
            login,
            password
        )
    }

    override fun getDefaultConnectionSettings(): ApiSettings {
        resetConnectionSettingsUseCase()
        return ApiSettings(
            ApiSettings.DEFAULT_URL_SERVICE_1C,
            ApiSettings.DEFAULT_LOGIN_SERVICE_1C,
            ApiSettings.DEFAULT_PASSWORD_SERVICE_1C
        )
    }

    override fun setConnectionSettings(settings: ApiSettings) {
        saveConnectionSettingsUseCase(settings)
    }

    companion object {
        private const val TEST_STRING = "test string"
    }
}