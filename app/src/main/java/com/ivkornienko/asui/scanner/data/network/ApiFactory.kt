package com.ivkornienko.asui.scanner.data.network

import retrofit2.Retrofit.Builder

class ApiFactory(
    builder: Builder,
    url: String
) {
    val apiService: ApiService = builder.baseUrl(url).build().create(ApiService::class.java)
}
