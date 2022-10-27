package com.ivkornienko.asui.scanner.domain.repository

import androidx.lifecycle.LiveData
import com.ivkornienko.asui.scanner.domain.entity.ProductInfo

interface ProductInfoRepository {

    fun getProductInfoList(): LiveData<List<ProductInfo>>

    fun getProductInfo(id: Long): LiveData<ProductInfo>

    suspend fun loadProductInfoByBarcode(barcode: String): Long

    suspend fun clearHistory()
}