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
    private val apiInterceptor: ApiInterceptor,
    private val retrofitBuilder: Retrofit.Builder,
    private val saveConnectionSettingsUseCase: SaveConnectionSettingsUseCase,
    private val readConnectionSettingsUseCase: ReadConnectionSettingsUseCase,
    private val resetConnectionSettingsUseCase: ResetConnectionSettingsUseCase
) : ConnectionSettingsRepository {

    override suspend fun testConnection(settings: ApiSettings): Boolean {
        apiInterceptor.setInterceptor(settings)
        val apiService = ApiFactory(retrofitBuilder).apiService(settings)

        val response = apiService.testConnection(TEST_STRING)
        return response.test_string == TEST_STRING
    }

    override fun getConnectionSettings(): ApiSettings {
        with(readConnectionSettingsUseCase()) {
            return ApiSettings(
                host = this.host.ifBlank { ApiSettings.DEFAULT_HOST_SERVICE_1C },
                base = this.base.ifBlank { ApiSettings.DEFAULT_BASE_1C },
                name = this.name.ifBlank { ApiSettings.DEFAULT_NAME_SERVICE_1C },
                login = this.login.ifBlank { ApiSettings.DEFAULT_LOGIN_SERVICE_1C },
                password = this.password.ifBlank { ApiSettings.DEFAULT_PASSWORD_SERVICE_1C }
            )
        }
    }

    override fun getDefaultConnectionSettings(): ApiSettings {
        resetConnectionSettingsUseCase()
        return ApiSettings(
            ApiSettings.DEFAULT_HOST_SERVICE_1C,
            ApiSettings.DEFAULT_BASE_1C,
            ApiSettings.DEFAULT_NAME_SERVICE_1C,
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