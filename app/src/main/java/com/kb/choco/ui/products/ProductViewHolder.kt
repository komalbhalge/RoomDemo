package com.kb.moviedb.ui.main.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kb.choco.databinding.ProductItemBinding
import com.kb.choco.data.db.entities.ProductEntity

class ProductViewHolder(
    val context: Context?,
    val binding: ProductItemBinding,
    val products: List<ProductEntity>
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    override fun onClick(v: View) {
        //Handle item click
    }
}