package com.ivkornienko.asui.scanner

interface SettingsRepository {

    suspend fun testConnectionSettings(settings: ApiSettings): Boolean

    suspend fun loadConnectionSettings(): ApiSettings

    suspend fun saveConnectionSettings(settings: ApiSettings)

    suspend fun defaultConnectionSettings(): ApiSettings
}