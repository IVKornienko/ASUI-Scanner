package com.ivkornienko.asui.scanner

data class ProductInfo(
    val id: Long,
    val timeScan: String,
    val state: String,
    val count: Int = 0,
    val exist: String,
    val fullName: String,
    val invNumber: String,
    val barcode: String,
    val serialNumber: String,
    val typeAsset: String,
    val operationDate: String,
    val organizationName: String,
    val storeName: String,
    val inventDate: String,
    val inAsui: Boolean,

    )
