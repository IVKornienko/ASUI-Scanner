package com.ivkornienko.asui.scanner.network

import com.google.gson.annotations.SerializedName
import com.ivkornienko.asui.scanner.ProductInfo

data class ProductInfoResponse(
    @SerializedName("error")
    val error: String?,

    @SerializedName("state")
    val state: String?,

    @SerializedName("count")
    val count: Int?,

    @SerializedName("exist")
    val exist: String?,

    @SerializedName("full_name")
    val fullName: String?,

    @SerializedName("inv_number")
    val invNumber: String?,

    @SerializedName("barcode")
    val barcode: String?,

    @SerializedName("serial_number")
    val serialNumber: String?,

    @SerializedName("type_asset")
    val typeAsset: String?,

    @SerializedName("operation_date")
    val operationDate: String?,

    @SerializedName("organization_name")
    val organizationName: String?,

    @SerializedName("store_name")
    val storeName: String?,

    @SerializedName("date_invent")
    val inventDate: String?,

    @SerializedName("in_other_inv")
    val inOtherInv: Boolean?,

    @SerializedName("has_children_inv")
    val hasChildrenInv: Boolean?,

    @SerializedName("in_asui")
    val inAsui: Boolean?
) {
    fun mapper(): ProductInfo {
        return ProductInfo(
            error = error ?: "",
            state = state ?: "",
            count = count ?: 0,
            exist = exist ?: "",
            fullName = fullName ?: "",
            invNumber = invNumber ?: "",
            barcode = barcode ?: "",
            serialNumber = serialNumber ?: "",
            typeAsset = typeAsset ?: "",
            operationDate = operationDate ?: "",
            organizationName = organizationName ?: "",
            storeName = storeName ?: "",
            inventDate = inventDate ?: "",
            inOtherInv = inOtherInv ?: false,
            hasChildrenInv = hasChildrenInv ?: false,
            inAsui = inAsui ?: false
        )
    }
}