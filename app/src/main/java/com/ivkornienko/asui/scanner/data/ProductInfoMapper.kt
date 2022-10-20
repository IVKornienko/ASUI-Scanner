package com.ivkornienko.asui.scanner.data

import com.ivkornienko.asui.scanner.data.database.ProductInfoDbModel
import com.ivkornienko.asui.scanner.domain.entity.ProductInfo
import com.ivkornienko.asui.scanner.data.network.ProductInfoDto
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

class ProductInfoMapper {

    fun mapDtoToDbModel(productInfo: ProductInfoDto) = ProductInfoDbModel(
        timeScan = System.currentTimeMillis(),
        state = productInfo.state,
        count = productInfo.count,
        exist = productInfo.exist,
        fullName = productInfo.fullName,
        invNumber = productInfo.invNumber,
        barcode = productInfo.barcode,
        serialNumber = productInfo.serialNumber,
        typeAsset = productInfo.typeAsset,
        operationDate = productInfo.operationDate,
        organizationName = productInfo.organizationName,
        storeName = productInfo.storeName,
        inventDate = productInfo.inventDate,
        inAsui = productInfo.inAsui,
        id = UNDEFINED_ID,
    )

    fun mapDbModelToEntity(productInfoDbModel: ProductInfoDbModel) = ProductInfo(
        id = productInfoDbModel.id,
        timeScan = convertTimestampToTime(productInfoDbModel.timeScan),
        state = productInfoDbModel.state,
        count = productInfoDbModel.count,
        exist = productInfoDbModel.exist,
        fullName = productInfoDbModel.fullName,
        invNumber = productInfoDbModel.invNumber,
        barcode = productInfoDbModel.barcode,
        serialNumber = productInfoDbModel.serialNumber,
        typeAsset = productInfoDbModel.typeAsset,
        operationDate = productInfoDbModel.operationDate,
        organizationName = productInfoDbModel.organizationName,
        storeName = productInfoDbModel.storeName,
        inventDate = productInfoDbModel.inventDate,
        inAsui = productInfoDbModel.inAsui,
    )

    private fun convertTimestampToTime(timestamp: Long): String {
        val date = Date(timestamp)
        val format = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault())
        return format.format(date)
    }

    companion object {
        private const val UNDEFINED_ID = 0L
        private const val DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss"
    }
}