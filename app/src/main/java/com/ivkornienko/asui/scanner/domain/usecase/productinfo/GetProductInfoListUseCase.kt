package com.ivkornienko.asui.scanner.domain.usecase.productinfo

import com.ivkornienko.asui.scanner.domain.repository.ProductInfoRepository

class GetProductInfoListUseCase(private val repository: ProductInfoRepository) {

    operator fun invoke() = repository.getProductInfoList()
}