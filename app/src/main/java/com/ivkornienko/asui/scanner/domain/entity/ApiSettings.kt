package com.ivkornienko.asui.scanner.domain.entity

data class ApiSettings (
    val host: String,
    val base: String,
    val name: String,
    val login: String,
    val password: String,
) {
    val url = String.format(FORMAT_URL_SERVICE_1C, host, base, name)

    companion object {
        const val FORMAT_URL_SERVICE_1C = "http://%s/%s/hs/%s/"
        const val DEFAULT_HOST_SERVICE_1C = "localhost"
        const val DEFAULT_BASE_1C = "InventMobile"
        const val DEFAULT_NAME_SERVICE_1C = "asui"
        const val DEFAULT_LOGIN_SERVICE_1C = "Admin"
        const val DEFAULT_PASSWORD_SERVICE_1C = "1"
    }
}
