package com.ivkornienko.asui.scanner.presentation.productinfo

import androidx.lifecycle.ViewModel
import com.ivkornienko.asui.scanner.domain.usecase.productinfo.GetProductInfoUseCase
import javax.inject.Inject

class ProductInfoViewModel @Inject constructor(
    private val getProductInfoUseCase: GetProductInfoUseCase
) : ViewModel() {

    fun getProductItem(productInfoId: Long) = getProductInfoUseCase(productInfoId)
}