package com.ivkornienko.asui.scanner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProductInfoViewModel(application: Application) : AndroidViewModel(application) {

    private val _productInfo = MutableLiveData<ProductInfo>()
    val productInfo: LiveData<ProductInfo>
        get() = _productInfo

    fun getProductItem(productInfoId: Long) {
        viewModelScope.launch {
            val repositoryImpl = ProductInfoRepositoryImpl(getApplication())
            val item = repositoryImpl.getProductInfo(productInfoId)
            _productInfo.value = item
        }
    }
}