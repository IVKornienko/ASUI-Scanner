package com.ivkornienko.asui.scanner

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiFactory(
    private val context: Context,
    private val apiSettings: ApiSettings
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

    val apiService = retrofit.create(ApiService::class.java)
}