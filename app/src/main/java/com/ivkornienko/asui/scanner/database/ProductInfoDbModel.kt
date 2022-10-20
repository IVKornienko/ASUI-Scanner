package com.ivkornienko.asui.scanner.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import retrofit2.http.Field

@Entity(tableName = "history_scan")
data class ProductInfoDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @Field("time_scan")
    val timeScan: Long,

    @Field("state")
    val state: String,

    @Field("count")
    val count: Int,

    @Field("exist")
    val exist: String,

    @Field("full_name")
    val fullName: String,

    @Field("inv_number")
    val invNumber: String,

    @Field("barcode")
    val barcode: String,

    @Field("serial_number")
    val serialNumber: String,

    @Field("type_asset")
    val typeAsset: String,

    @Field("operation_date")
    val operationDate: String,

    @Field("organization_name")
    val organizationName: String,

    @Field("store_name")
    val storeName: String,

    @Field("invent_date")
    val inventDate: String,

    @Field("in_asui")
    val inAsui: Boolean,
)