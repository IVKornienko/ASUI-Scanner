package com.ivkornienko.asui.scanner

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductInfo(
    val error: String = "",

    val state: String = "",
    val count: Int = 0,
    val exist: String = "",
    val fullName: String = "",
    val invNumber: String = "",
    val barcode: String = "",
    val serialNumber: String = "",
    val typeAsset: String = "",
    val operationDate: String = "",
    val organizationName: String = "",
    val storeName: String = "",
    val dateInvent: String = "",
    val inOtherInv: Boolean = false,
    val hasChildrenInv: Boolean = false,
    val inAsui: Boolean = false

) : Parcelable
