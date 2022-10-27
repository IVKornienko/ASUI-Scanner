package com.ivkornienko.asui.scanner.domain.usecase.connectionsettings

import com.ivkornienko.asui.scanner.domain.repository.ConnectionSettingsRepository
import javax.inject.Inject

class GetConnectionSettingsUseCase @Inject constructor
    (private val repository: ConnectionSettingsRepository) {

    operator fun invoke() = repository.getConnectionSettings()
}