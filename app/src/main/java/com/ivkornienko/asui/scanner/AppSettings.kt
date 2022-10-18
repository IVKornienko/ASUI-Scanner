package com.ivkornienko.asui.scanner

interface AppSettings {

    fun setApiSettings(settings: ApiSettings)

    fun clearApiSettings()

    fun getURLService1C(): String

    fun getLoginService1C(): String

    fun getPasswordService1C(): String
}