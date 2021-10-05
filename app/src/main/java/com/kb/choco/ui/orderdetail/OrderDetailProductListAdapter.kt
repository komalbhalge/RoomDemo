package com.kb.choco.ui.orderdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kb.choco.R
import com.kb.choco.databinding.OrderProductItemBinding
import com.kb.choco.data.db.entities.ProductEntity
import com.kb.choco.ui.orderdetail.OrderDetailProductListAdapter.ProductFromOrderViewHolder
import com.kb.choco.util.extensions.appendCurrency

class OrderDetailProductListAdapter : RecyclerView.Adapter<ProductFromOrderViewHolder>() {

    var productList = mutableListOf<ProductEntity>()
    var totalPrice = MutableLiveData<Double>(0.0)

    var counter: Int = 1
    var isActivateCounter = false

    fun setProducts(products: List<ProductEntity>) {
        //Reset total price
        totalPrice.value = 0.0
        this.productList = products.toMutableList()
        notifyDataSetChanged()
    }

    inner class ProductFromOrderViewHolder(val binding: OrderProductItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductFromOrderViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = OrderProductItemBinding.inflate(inflater, parent, false)
        return ProductFromOrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductFromOrderViewHolder, position: Int) {
        val product = productList[position]
        with(holder.binding) {
            if (isActivateCounter) {
                counterLayout.visibility = View.VISIBLE
            } else {
                counterLayout.visibility = View.GONE
            }
            tvTitle.text = product.name
            tvDescription.text = product.description
            Glide.with(ivImage.context)
                .load(product.photo)
                .placeholder(R.drawable.ic_empty_list)
                .into(ivImage)
            //Set counter to 1
            tvCount.text = "1"

            btnPlus.setOnClickListener {
                counter++
                tvCount.text = counter.toString()
            }
            btnMinus.setOnClickListener {
                if (counter > 1) { //Do not let counter below minimum qty i.e 1
                    counter--
                    tvCount.text = counter.toString()
                }
            }
            tvPrice.text = product.price.toString().appendCurrency()
            //Compute total price
            totalPrice.value = totalPrice.value?.plus(product.price)
        }
    }

    fun activateCounter(activate: Boolean) {
        totalPrice.value = 0.0
        this.isActivateCounter = activate
        notifyDataSetChanged() //need to call it for the child views to be re-created with buttons.
    }

    override fun getItemCount(): Int {
        return productList.size
    }


}