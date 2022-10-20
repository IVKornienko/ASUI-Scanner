package com.ivkornienko.asui.scanner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repositoryImpl =
        ProductInfoRepositoryImpl(getApplication())

    val history = repositoryImpl.getListProductInfo()

    fun clearHistory() {
        viewModelScope.launch { repositoryImpl.clearHistory() }
    }
}