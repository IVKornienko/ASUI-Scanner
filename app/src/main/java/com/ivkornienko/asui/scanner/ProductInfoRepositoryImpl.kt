package com.ivkornienko.asui.scanner

import android.content.Context

class ProductInfoRepositoryImpl(
    private val context: Context,
) : ProductInfoRepository {

    override suspend fun getInfoByBarcode(barcode: String): ProductInfo {

        val sourceSettings = AppSettingSharedPreferences(context)
        val settings = ApiSettings(sourceSettings.getURLService1C(), sourceSettings.getLoginService1C(), sourceSettings.getPasswordService1C())

        if (settings.url.isBlank() || settings.login.isBlank()) {
            throw java.lang.RuntimeException("Connection settings is empty")
        }

        val apiService = ApiFactory(context, settings).apiService
        val response = apiService.get_infoByBarcode(barcode)

        return response.mapper()
    }
}