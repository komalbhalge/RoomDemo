package com.kb.choco.ui.orders

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kb.choco.databinding.OrderItemBinding
import com.kb.choco.data.db.entities.OrderEntity

class OrderViewHolder(
    val context: Context?,
    val binding: OrderItemBinding,
    val products: List<OrderEntity>,
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    init {
        itemView.rootView.setOnClickListener { onItemClicked.invoke(absoluteAdapterPosition) }
    }


    override fun onClick(v: View) {

    }

}