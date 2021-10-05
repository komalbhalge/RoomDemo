package com.kb.choco.ui.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kb.choco.databinding.OrderItemBinding
import com.kb.choco.data.db.entities.OrderEntity

class OrderListAdapter(
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<OrderViewHolder>() {

    var orderList = mutableListOf<OrderEntity>()

    fun setProducts(products: List<OrderEntity>) {
        this.orderList = products.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = OrderItemBinding.inflate(inflater, parent, false)
        return OrderViewHolder(parent.context, binding, orderList, onItemClicked)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        with(holder.binding) {

            tvOrderTitle.text = ("Order No. ").plus(order.orderId.toString())
            tvOrderCreation.text = order.order_date.toString()

        }
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

}