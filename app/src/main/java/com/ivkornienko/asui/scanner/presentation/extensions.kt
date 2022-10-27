package com.ivkornienko.asui.scanner

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach

suspend fun <T> StateFlow<T?>.collectOnce(reset: (T?) -> Unit, action: (value: T) -> Unit) {
    this.filterNotNull().onEach { reset.invoke(null) }.collect {
        action.invoke(it)
    }
}


