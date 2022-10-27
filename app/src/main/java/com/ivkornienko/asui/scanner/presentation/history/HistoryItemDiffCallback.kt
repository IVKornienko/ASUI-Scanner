package com.ivkornienko.asui.scanner.presentation.history

import androidx.recyclerview.widget.DiffUtil
import com.ivkornienko.asui.scanner.domain.entity.ProductInfo

class HistoryItemDiffCallback:  DiffUtil.ItemCallback<ProductInfo>() {
    override fun areItemsTheSame(oldItem: ProductInfo, newItem: ProductInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductInfo, newItem: ProductInfo): Boolean {
        return oldItem == newItem
    }
}