package com.ivkornienko.asui.scanner.domain.repository

import com.ivkornienko.asui.scanner.domain.entity.ApiSettings

interface ConnectionSettingsRepository {

    suspend fun testConnection(settings: ApiSettings): Boolean

    fun getConnectionSettings(): ApiSettings

    fun getDefaultConnectionSettings(): ApiSettings

    fun setConnectionSettings(settings: ApiSettings)

}