package com.ivkornienko.asui.scanner.domain.usecase.productinfo

import com.ivkornienko.asui.scanner.domain.repository.ProductInfoRepository

class GetProductInfoUseCase(private val repository: ProductInfoRepository) {

    suspend operator fun invoke(id: Long) = repository.getProductInfo(id)
}