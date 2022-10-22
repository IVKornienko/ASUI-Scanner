package com.ivkornienko.asui.scanner.domain.usecase.productinfo

import com.ivkornienko.asui.scanner.domain.repository.ProductInfoRepository
import javax.inject.Inject

class ClearHistoryUseCase @Inject constructor
    (private val repository: ProductInfoRepository) {

    suspend operator fun invoke() = repository.clearHistory()
}