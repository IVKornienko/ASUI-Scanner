package com.ivkornienko.asui.scanner.domain.usecase.connectionsettings

import com.ivkornienko.asui.scanner.domain.entity.ApiSettings
import com.ivkornienko.asui.scanner.domain.repository.ConnectionSettingsRepository

class TestConnectionUseCase(private val repository: ConnectionSettingsRepository) {

    suspend operator fun invoke(settings: ApiSettings) = repository.testConnection(settings)

}