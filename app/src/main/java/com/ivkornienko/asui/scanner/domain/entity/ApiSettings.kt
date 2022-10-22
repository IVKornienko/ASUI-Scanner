package com.ivkornienko.asui.scanner.domain.entity

import javax.inject.Inject

data class ApiSettings @Inject constructor(
    val url: String,
    val login: String,
    val password: String,
) {

    companion object {
        const val DEFAULT_URL_SERVICE_1C = "http://10.0.2.2/infobase/hs/asui/"
        const val DEFAULT_LOGIN_SERVICE_1C = "Admin"
        const val DEFAULT_PASSWORD_SERVICE_1C = "1"
    }
}
