package com.ivkornienko.asui.scanner.domain.repository

import androidx.lifecycle.LiveData
import com.ivkornienko.asui.scanner.domain.entity.ProductInfo

interface ProductInfoRepository {

    fun getProductInfoList(): LiveData<List<ProductInfo>>

    suspend fun loadProductInfoByBarcode(barcode: String): Long

    suspend fun getProductInfo(id: Long): ProductInfo

    suspend fun clearHistory()
}