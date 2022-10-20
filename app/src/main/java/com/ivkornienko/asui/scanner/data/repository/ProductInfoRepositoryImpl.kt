package com.ivkornienko.asui.scanner.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ivkornienko.asui.scanner.data.ProductInfoMapper
import com.ivkornienko.asui.scanner.data.database.ProductInfoDao
import com.ivkornienko.asui.scanner.data.database.ProductInfoDbModel
import com.ivkornienko.asui.scanner.data.network.ApiFactory
import com.ivkornienko.asui.scanner.data.network.ProductInfoDto
import com.ivkornienko.asui.scanner.domain.entity.ApiSettings
import com.ivkornienko.asui.scanner.domain.entity.ProductInfo
import com.ivkornienko.asui.scanner.domain.repository.ProductInfoRepository
import com.ivkornienko.asui.scanner.domain.repository.StorageConnectionSettingsRepository
import com.ivkornienko.asui.scanner.domain.usecase.connectionsettings.ReadConnectionSettingsUseCase

class ProductInfoRepositoryImpl(
    private val productInfoDao: ProductInfoDao,
    private val productInfoMapper: ProductInfoMapper,
    repository: StorageConnectionSettingsRepository
) : ProductInfoRepository {

    private val readConnectionSettingsUseCase = ReadConnectionSettingsUseCase(repository)


    override fun getProductInfoList(): LiveData<List<ProductInfo>> {
        return Transformations.map(productInfoDao.getListProductInfo()) { list ->
            list.map {
                productInfoMapper.mapDbModelToEntity(it)
            }
        }
    }

    override suspend fun loadProductInfoByBarcode(barcode: String): Long {
        val settings = readConnectionSettingsUseCase()

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

    override suspend fun getProductInfo(id: Long): ProductInfo {
        val productInfoDb = productInfoDao.getProductInfo(id)
        return productInfoMapper.mapDbModelToEntity(productInfoDb)
    }

    override suspend fun clearHistory() {
        productInfoDao.clearHistoryScan()
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