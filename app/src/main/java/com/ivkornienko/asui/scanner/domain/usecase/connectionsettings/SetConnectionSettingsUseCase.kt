package com.ivkornienko.asui.scanner.domain.usecase.connectionsettings

import com.ivkornienko.asui.scanner.domain.entity.ApiSettings
import com.ivkornienko.asui.scanner.domain.repository.ConnectionSettingsRepository

class SetConnectionSettingsUseCase(private val repository: ConnectionSettingsRepository) {

    operator fun invoke(settings: ApiSettings) = repository.setConnectionSettings(settings)
}