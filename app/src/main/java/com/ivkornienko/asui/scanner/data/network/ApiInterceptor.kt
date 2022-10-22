package com.ivkornienko.asui.scanner.data.network

import com.ivkornienko.asui.scanner.di.ApplicationScope
import com.ivkornienko.asui.scanner.domain.entity.ApiSettings
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

@ApplicationScope
class ApiInterceptor : Interceptor {
    private var login: String = ""
    private var password: String = ""

    fun setInterceptor(settings: ApiSettings) {
        login = settings.login
        password = settings.password
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val credentials = Credentials.basic(login, password)
        request = request.newBuilder().header("Authorization", credentials).build()
        return chain.proceed(request)
    }
}