package com.ivkornienko.asui.scanner.domain.usecase.connectionsettings

import com.ivkornienko.asui.scanner.domain.repository.ConnectionSettingsRepository

class GetDefaultConnectionSettingsUseCase(private val repository: ConnectionSettingsRepository) {

    operator fun invoke() = repository.getDefaultConnectionSettings()
}