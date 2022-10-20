package com.ivkornienko.asui.scanner

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ivkornienko.asui.scanner.database.AppDatabase
import com.ivkornienko.asui.scanner.database.ProductInfoDbModel
import com.ivkornienko.asui.scanner.network.ApiFactory
import com.ivkornienko.asui.scanner.network.ProductInfoDto

class ProductInfoRepositoryImpl(
    application: Application
) : ProductInfoRepository {

    private val productInfoDao = AppDatabase.getInstance(application).productInfoDao()
    private val productInfoMapper = ProductInfoMapper()

    override fun getListProductInfo(): LiveData<List<ProductInfo>> {
        return Transformations.map(productInfoDao.getListProductInfo()) { list ->
            list.map {
                productInfoMapper.mapDbModelToEntity(it)
            }
        }
    }

    override suspend fun getProductInfoId(appSettings: AppSettings, barcode: String): Long {

        val settings = ApiSettings(
            appSettings.getURLService1C(),
            appSettings.getLoginService1C(),
            appSettings.getPasswordService1C()
        )
        if (settings.url.isBlank() || settings.login.isBlank()) {
            throw java.lang.RuntimeException("Connection settings is empty")
        }

        val productInfoDto = getProductInfoByBarcodeFromDto(settings, barcode)
        val error = productInfoDto.error ?: ""
        if (error.isNotBlank()) {
            throw RuntimeException(productInfoDto.error)
        }

        val productInfoDb = productInfoMapper.mapDtoToDbModel(productInfoDto)
        val productInfoId = saveProductInfoDto(productInfoDb)
        return productInfoId
    }

    override suspend fun clearHistory() {
        productInfoDao.clearHistoryScan()
    }

    override suspend fun getProductInfo(id: Long): ProductInfo {
        val productInfoDb = productInfoDao.getProductInfo(id)
        return productInfoMapper.mapDbModelToEntity(productInfoDb)
    }

    private suspend fun getProductInfoByBarcodeFromDto(
        settings: ApiSettings,
        barcode: String
    ): ProductInfoDto {
        val apiService = ApiFactory(settings).apiService
        return apiService.getInfoByBarcode(barcode)
    }

    private suspend fun saveProductInfoDto(productInfoDb: ProductInfoDbModel): Long {
        return productInfoDao.addProductInfo(productInfoDb)
    }
}