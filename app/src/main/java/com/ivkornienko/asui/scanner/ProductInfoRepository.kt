package com.ivkornienko.asui.scanner

import androidx.lifecycle.LiveData

interface ProductInfoRepository {

    fun getListProductInfo(): LiveData<List<ProductInfo>>

    suspend fun getProductInfoId(appSettings: AppSettings, barcode: String): Long

    suspend fun clearHistory()

    suspend fun getProductInfo(id: Long): ProductInfo

}