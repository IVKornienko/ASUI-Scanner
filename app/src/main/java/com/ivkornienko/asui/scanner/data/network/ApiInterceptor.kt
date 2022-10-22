package com.ivkornienko.asui.scanner.data.network

import com.ivkornienko.asui.scanner.domain.entity.ApiSettings
import okhttp3.Credentials
import okhttp3.Interceptor

class ApiInterceptor(settings: ApiSettings) : Interceptor {
    private var credentials: String = Credentials.basic(settings.login, settings.password)

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization", credentials).build()
        return chain.proceed(request)
    }
}