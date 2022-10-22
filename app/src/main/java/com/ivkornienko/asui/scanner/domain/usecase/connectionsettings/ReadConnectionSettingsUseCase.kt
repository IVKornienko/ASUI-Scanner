package com.ivkornienko.asui.scanner.domain.usecase.connectionsettings

import com.ivkornienko.asui.scanner.domain.repository.StorageConnectionSettingsRepository
import javax.inject.Inject

class ReadConnectionSettingsUseCase @Inject constructor
    (private val repository: StorageConnectionSettingsRepository) {

    operator fun invoke() = repository.readConnectionSettings()

}