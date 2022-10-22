package com.ivkornienko.asui.scanner.data.network

import com.ivkornienko.asui.scanner.domain.entity.ApiSettings
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiFactory(
    interceptor: ApiInterceptor,
    settings: ApiSettings
) {
    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(interceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(settings.url)
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
