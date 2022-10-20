package com.ivkornienko.asui.scanner.presentation.productinfo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ivkornienko.asui.scanner.data.ProductInfoMapper
import com.ivkornienko.asui.scanner.data.database.AppDatabase
import com.ivkornienko.asui.scanner.data.repository.ProductInfoRepositoryImpl
import com.ivkornienko.asui.scanner.data.repository.StorageConnectionSettingsRepositoryImpl
import com.ivkornienko.asui.scanner.domain.entity.ProductInfo
import com.ivkornienko.asui.scanner.domain.usecase.productinfo.GetProductInfoUseCase
import kotlinx.coroutines.launch

class ProductInfoViewModel(application: Application) : AndroidViewModel(application) {

    private val productInfoDao = AppDatabase.getInstance(application).productInfoDao()
    private val productInfoMapper = ProductInfoMapper()
    private val storageConnectionSettingsRepository =
        StorageConnectionSettingsRepositoryImpl(application)

    private val repository = ProductInfoRepositoryImpl(
        productInfoDao,
        productInfoMapper,
        storageConnectionSettingsRepository
    )

    private val getProductInfoUseCase = GetProductInfoUseCase(repository)

    private val _productInfo = MutableLiveData<ProductInfo>()
    val productInfo: LiveData<ProductInfo>
        get() = _productInfo

    fun getProductItem(productInfoId: Long) {
        viewModelScope.launch {
            val item = getProductInfoUseCase(productInfoId)
            _productInfo.value = item
        }
    }
}