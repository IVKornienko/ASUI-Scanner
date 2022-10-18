package com.ivkornienko.asui.scanner

data class ApiSettings (
    val url: String = DEFAULT_URL_SERVICE_1C,
    val login: String = DEFAULT_LOGIN_SERVICE_1C,
    val password: String = DEFAULT_PASSWORD_SERVICE_1C
) {

    companion object {
        private const val DEFAULT_URL_SERVICE_1C = "http://10.0.2.2/infobase/hs/asui/"
        private const val DEFAULT_LOGIN_SERVICE_1C = "Admin"
        private const val DEFAULT_PASSWORD_SERVICE_1C = "1"
    }
}


