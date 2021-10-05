package com.kb.moviedb.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kb.choco.R
import com.kb.choco.data.db.entities.ProductEntity
import com.kb.choco.databinding.ProductItemBinding
import com.kb.choco.ui.products.ProductSelectListener
import com.kb.choco.util.extensions.appendCurrency

class ProductListAdapter(
    private val productSelectListener: ProductSelectListener
) : RecyclerView.Adapter<ProductViewHolder>() {

    var productList = mutableListOf<ProductEntity>()
    private var selectedProductCount = 0
    fun setProducts(products: List<ProductEntity>) {
        this.productList = products.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductItemBinding.inflate(inflater, parent, false)
        return ProductViewHolder(parent.context, binding, productList)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        //update UI after adding all products to order unslect
        updateSelection(holder, product)
        updateUI(holder, product)
    }

    private fun updateSelection(holder: ProductViewHolder, product: ProductEntity) {
        with(holder.binding) {
            //update UI after adding all products to order unslect
            if (product.selected == true) {
                ivSelected.visibility = View.VISIBLE
            } else {
                ivSelected.visibility = View.GONE
            }
        }
    }

    private fun updateUI(holder: ProductViewHolder, product: ProductEntity) {
        with(holder.binding) {
            tvTitle.text = product.name
            tvDescription.text = product.description
            tvPrice.text = product.price.toString().appendCurrency()
            Glide.with(ivImage.context)
                .load(product.photo)
                .placeholder(R.drawable.ic_empty_list)
                .into(ivImage)

            carView.setOnClickListener {
                if (product.selected == true) {
                    ivSelected.visibility = View.GONE
                    product.selected = false
                    selectedProductCount--
                    if (selectedProductCount == 0) {
                        productSelectListener.onProductAction(false)
                    }
                } else {
                    ivSelected.visibility = View.VISIBLE
                    product.selected = true
                    selectedProductCount++
                    productSelectListener.onProductAction(true)
                }
            }

        }

    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun getSelectedProducts(): List<ProductEntity> {
        val selectedList = mutableListOf<ProductEntity>()
        for (product in productList) {
            if (product.selected == true) {
                selectedList.add(product)
                //Mark selected as false to update the list after added in OrdersList
                product.selected = false
            }
        }

        //Reset selected products count
        selectedProductCount = 0
        return selectedList
    }

    fun unSelectProducts() {
        //The selected products will
        notifyDataSetChanged()
    }

}