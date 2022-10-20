package com.ivkornienko.asui.scanner.domain.repository

import com.ivkornienko.asui.scanner.domain.entity.ApiSettings

interface StorageConnectionSettingsRepository {

    fun saveConnectionSettings(settings: ApiSettings)

    fun readConnectionSettings(): ApiSettings

    fun resetConnectionSettings()

}