package com.ivkornienko.asui.scanner.data.network

import com.ivkornienko.asui.scanner.domain.EmptyConnectionException
import com.ivkornienko.asui.scanner.domain.entity.ApiSettings
import retrofit2.Retrofit.Builder

class ApiFactory(
    private val builder: Builder
) {

    fun apiService(settings: ApiSettings): ApiService {
        if (settings.host.isBlank() || settings.base.isBlank() || settings.name.isBlank()) {
            throw EmptyConnectionException
        }

        return builder.baseUrl(settings.url).build().create(ApiService::class.java)
    }
}
