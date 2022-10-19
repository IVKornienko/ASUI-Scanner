package com.ivkornienko.asui.scanner

interface ProductInfoRepository {

    suspend fun getInfoByBarcode(barcode: String): ProductInfo

}