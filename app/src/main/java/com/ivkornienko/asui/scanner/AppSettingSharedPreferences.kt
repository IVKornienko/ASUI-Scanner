package com.ivkornienko.asui.scanner

import android.content.Context

class AppSettingSharedPreferences(appContext: Context) : AppSettings {

    private val sharedPreferences =
        appContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun setApiSettings(settings: ApiSettings) {
        setPreference(PREFERENCES_NAME, settings.url)
        setPreference(PREFERENCE_LOGIN_SERVICE1C, settings.login)
        setPreference(PREFERENCE_PASSWORD_SERVICE1C, settings.password)
    }

    override fun clearApiSettings() {
        setPreference(PREFERENCES_NAME, null)
        setPreference(PREFERENCE_LOGIN_SERVICE1C, null)
        setPreference(PREFERENCE_PASSWORD_SERVICE1C, null)
    }

    override fun getURLService1C(): String {
        return sharedPreferences.getString(PREFERENCE_URL_SERVICE1C, "")
            ?: throw java.lang.RuntimeException()
    }

    override fun getLoginService1C(): String {
        return sharedPreferences.getString(PREFERENCE_LOGIN_SERVICE1C, "")
            ?: throw java.lang.RuntimeException()
    }

    override fun getPasswordService1C(): String {
        return sharedPreferences.getString(
            PREFERENCE_PASSWORD_SERVICE1C, ""
        ) ?: throw java.lang.RuntimeException()
    }

    private fun setPreference(name: String, value: String?) {
        val editor = sharedPreferences.edit()
        if (value == null) editor.remove(name)
        else editor.putString(name, value)
        editor.apply()
    }

    companion object {
        private const val PREFERENCES_NAME = "settings_Service1C"
        private const val PREFERENCE_URL_SERVICE1C = "urlService1C"
        private const val PREFERENCE_LOGIN_SERVICE1C = "loginService1C"
        private const val PREFERENCE_PASSWORD_SERVICE1C = "passwordService1C"
    }

}