package com.ivkornienko.asui.scanner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ivkornienko.asui.scanner.databinding.ItemProductInfoBinding

class HistoryAdapter : ListAdapter<ProductInfo, HistoryViewHolder>(HistoryItemDiffCallback()) {

    var onHistoryItemClickListener: ((ProductInfo) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemProductInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val productInfo = getItem(position)
        with(holder.binding) {
            tvFullName.text = productInfo.fullName
            tvBarcode.text = productInfo.barcode
            tvInvNumber.text = productInfo.invNumber

            root.setOnClickListener {
                onHistoryItemClickListener?.invoke(productInfo)
            }
        }
    }

}