package com.ivkornienko.asui.scanner

import androidx.recyclerview.widget.DiffUtil

class HistoryItemDiffCallback:  DiffUtil.ItemCallback<ProductInfo>() {
    override fun areItemsTheSame(oldItem: ProductInfo, newItem: ProductInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductInfo, newItem: ProductInfo): Boolean {
        return oldItem == newItem
    }
}