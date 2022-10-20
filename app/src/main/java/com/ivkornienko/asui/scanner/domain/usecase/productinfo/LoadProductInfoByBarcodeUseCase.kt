package com.ivkornienko.asui.scanner.domain.usecase.productinfo

import com.ivkornienko.asui.scanner.domain.repository.ProductInfoRepository

class LoadProductInfoByBarcodeUseCase(private val repository: ProductInfoRepository) {

    suspend operator fun invoke(barcode: String) =
        repository.loadProductInfoByBarcode(barcode)
}