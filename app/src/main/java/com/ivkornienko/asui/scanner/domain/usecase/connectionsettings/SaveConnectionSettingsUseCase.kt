package com.ivkornienko.asui.scanner.domain.usecase.connectionsettings

import com.ivkornienko.asui.scanner.domain.entity.ApiSettings
import com.ivkornienko.asui.scanner.domain.repository.StorageConnectionSettingsRepository

class SaveConnectionSettingsUseCase(private val repository: StorageConnectionSettingsRepository) {

    operator fun invoke(settings: ApiSettings) = repository.saveConnectionSettings(settings)

}