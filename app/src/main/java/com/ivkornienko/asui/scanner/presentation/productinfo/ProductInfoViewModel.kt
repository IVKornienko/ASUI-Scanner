package com.ivkornienko.asui.scanner.presentation.productinfo

import androidx.lifecycle.ViewModel
import com.ivkornienko.asui.scanner.domain.usecase.productinfo.GetProductInfoUseCase
import javax.inject.Inject

class ProductInfoViewModel @Inject constructor(
    private val getProductInfoUseCase: GetProductInfoUseCase
) : ViewModel() {

//    private val productInfoDao = AppDatabase.getInstance(context).productInfoDao()
//    private val productInfoMapper = ProductInfoMapper()
//    private val storageConnectionSettingsRepository =
//        StorageConnectionSettingsRepositoryImpl(context)
//
//    private val repository = ProductInfoRepositoryImpl(
//        productInfoDao,
//        productInfoMapper,
//        storageConnectionSettingsRepository
//    )
//
//    private val getProductInfoUseCase = GetProductInfoUseCase(repository)


    fun getProductItem(productInfoId: Long) = getProductInfoUseCase(productInfoId)
}