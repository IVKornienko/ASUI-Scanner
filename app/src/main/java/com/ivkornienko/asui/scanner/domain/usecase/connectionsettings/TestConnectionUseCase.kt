package com.ivkornienko.asui.scanner.domain.usecase.connectionsettings

import com.ivkornienko.asui.scanner.domain.entity.ApiSettings
import com.ivkornienko.asui.scanner.domain.repository.ConnectionSettingsRepository
import javax.inject.Inject

class TestConnectionUseCase @Inject constructor
    (private val repository: ConnectionSettingsRepository) {

    suspend operator fun invoke(settings: ApiSettings) = repository.testConnection(settings)

}