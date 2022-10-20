package com.ivkornienko.asui.scanner.domain.usecase.productinfo

import com.ivkornienko.asui.scanner.domain.repository.ProductInfoRepository

class ClearHistoryUseCase(private val repository: ProductInfoRepository) {

    suspend operator fun invoke() = repository.clearHistory()
}