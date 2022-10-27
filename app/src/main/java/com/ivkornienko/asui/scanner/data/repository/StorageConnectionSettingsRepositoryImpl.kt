package com.ivkornienko.asui.scanner.data.repository

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.ivkornienko.asui.scanner.domain.entity.ApiSettings
import com.ivkornienko.asui.scanner.domain.repository.StorageConnectionSettingsRepository
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class StorageConnectionSettingsRepositoryImpl @Inject constructor(
    application: Application
) : StorageConnectionSettingsRepository {

    private val sharedPreferences =
        application.getSharedPreferences(
            PREFERENCES_NAME,
            Context.MODE_PRIVATE,
        )

    private var hostOption by sharedPreferences.string(key = { PREFERENCE_HOST_SERVICE1C })
    private var baseOption by sharedPreferences.string(key = { PREFERENCE_BASE_SERVICE1C })
    private var nameOption by sharedPreferences.string(key = { PREFERENCE_NAME_SERVICE1C })
    private var loginOption by sharedPreferences.string(key = { PREFERENCE_LOGIN_SERVICE1C })
    private var passwordOption by sharedPreferences.string(key = { PREFERENCE_PASSWORD_SERVICE1C })

    override fun saveConnectionSettings(settings: ApiSettings) {
        with(settings) {
            hostOption = this.host
            baseOption = this.base
            nameOption = this.name
            loginOption = this.login
            passwordOption = this.password
        }
    }

    override fun readConnectionSettings(): ApiSettings {
        return ApiSettings(
            host = hostOption ?: "",
            base = baseOption ?: "",
            name = nameOption ?: "",
            login = loginOption ?: "",
            password = passwordOption ?: "",
        )
    }

    override fun resetConnectionSettings() {
        hostOption = null
        baseOption = null
        nameOption = null
        loginOption = null
        passwordOption = null
    }


    private fun SharedPreferences.string(
        key: (KProperty<*>) -> String = KProperty<*>::name
    ): ReadWriteProperty<Any, String?> =
        object : ReadWriteProperty<Any, String?> {
            override fun getValue(thisRef: Any, property: KProperty<*>) =
                getString(key(property), "")

            override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) {
                if (value == null) {
                    edit().remove(key(property)).apply()
                } else {
                    edit().putString(key(property), value).apply()
                }
            }
        }

    companion object {
        private const val PREFERENCES_NAME = "settings_Service1C"

        private const val PREFERENCE_HOST_SERVICE1C = "host"
        private const val PREFERENCE_BASE_SERVICE1C = "base"
        private const val PREFERENCE_NAME_SERVICE1C = "name"
        private const val PREFERENCE_LOGIN_SERVICE1C = "login"
        private const val PREFERENCE_PASSWORD_SERVICE1C = "password"
    }
}