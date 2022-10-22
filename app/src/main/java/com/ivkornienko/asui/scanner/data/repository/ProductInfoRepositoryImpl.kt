package com.ivkornienko.asui.scanner.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ivkornienko.asui.scanner.data.ProductInfoMapper
import com.ivkornienko.asui.scanner.data.database.ProductInfoDao
import com.ivkornienko.asui.scanner.data.network.ApiFactory
import com.ivkornienko.asui.scanner.data.network.ApiInterceptor
import com.ivkornienko.asui.scanner.domain.entity.ProductInfo
import com.ivkornienko.asui.scanner.domain.repository.ProductInfoRepository
import com.ivkornienko.asui.scanner.domain.repository.StorageConnectionSettingsRepository
import com.ivkornienko.asui.scanner.domain.usecase.connectionsettings.ReadConnectionSettingsUseCase
import javax.inject.Inject

class ProductInfoRepositoryImpl @Inject constructor(
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

        val apiInterceptor = ApiInterceptor(settings)
        val apiService = ApiFactory(apiInterceptor, settings).apiService
        val productInfoDto = apiService.getInfoByBarcode(barcode)

        val error = productInfoDto.error ?: ""
        if (error.isNotBlank()) {
            throw RuntimeException(productInfoDto.error)
        }

        val productInfoDb = productInfoMapper.mapDtoToDbModel(productInfoDto)
        val productInfoId = productInfoDao.addProductInfo(productInfoDb)
        return productInfoId
    }

    override fun getProductInfo(id: Long): LiveData<ProductInfo> {
        return Transformations.map(productInfoDao.getProductInfo(id)) {
            productInfoMapper.mapDbModelToEntity(it)
        }
    }

    override suspend fun clearHistory() {
        productInfoDao.clearHistoryScan()
    }
}