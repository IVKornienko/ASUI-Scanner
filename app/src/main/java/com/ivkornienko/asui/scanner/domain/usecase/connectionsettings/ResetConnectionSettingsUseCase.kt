package com.ivkornienko.asui.scanner.domain.usecase.connectionsettings

import com.ivkornienko.asui.scanner.domain.repository.StorageConnectionSettingsRepository

class ResetConnectionSettingsUseCase(private val repository: StorageConnectionSettingsRepository) {

    operator fun invoke() = repository.resetConnectionSettings()

}