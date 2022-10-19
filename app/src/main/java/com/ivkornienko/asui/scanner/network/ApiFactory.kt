package com.ivkornienko.asui.scanner.network

import com.ivkornienko.asui.scanner.ApiSettings
import com.ivkornienko.asui.scanner.BasicAuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiFactory(
    apiSettings: ApiSettings
) {
    private val client = OkHttpClient.Builder()
        .addInterceptor(
            BasicAuthInterceptor(
                apiSettings.login,
                apiSettings.password
            )
        ).build()

    private val retrofit = Retrofit.Builder()
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(apiSettings.url)
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}