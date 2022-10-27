package com.ivkornienko.asui.scanner.domain.usecase.productinfo

import com.ivkornienko.asui.scanner.domain.repository.ProductInfoRepository
import javax.inject.Inject

class GetProductInfoUseCase @Inject constructor
    (private val repository: ProductInfoRepository) {

    operator fun invoke(id: Long) = repository.getProductInfo(id)
}