package com.ivkornienko.asui.scanner

import com.ivkornienko.asui.scanner.network.ApiFactory

class ProductInfoRepositoryImpl(
    private val appSettings: AppSettings
) : ProductInfoRepository {

    override suspend fun getInfoByBarcode(barcode: String): ProductInfo {

        val settings = ApiSettings(appSettings.getURLService1C(), appSettings.getLoginService1C(), appSettings.getPasswordService1C())

        if (settings.url.isBlank() || settings.login.isBlank()) {
            throw java.lang.RuntimeException("Connection settings is empty")
        }

        val apiService = ApiFactory(settings).apiService
        val response = apiService.get_infoByBarcode(barcode)

        return response.mapper()
    }
}