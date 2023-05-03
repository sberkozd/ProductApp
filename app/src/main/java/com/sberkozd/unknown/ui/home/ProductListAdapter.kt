package com.sberkozd.unknown.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sberkozd.unknown.databinding.ItemProductsBinding
import com.sberkozd.unknown.models.Product

class ProductListAdapter(val clickListener: (product: Product) -> Unit
) : ListAdapter<Product, ProductListAdapter.ProductViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.binding.apply {
            ivProductImage.load(getItem(position).thumbnail)
            tvProductName.text = getItem(position).title
            tvProductPrice.text = "$" + getItem(position).price.toString()
            root.setOnClickListener {
                clickListener(getItem(holder.adapterPosition))
            }
        }
    }

    override fun submitList(list: MutableList<Product>?) {
        super.submitList(list?.map { it.copy() }?.toMutableList())
    }

    class ProductViewHolder(val binding: ItemProductsBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Product> =
            object : DiffUtil.ItemCallback<Product>() {
                override fun areItemsTheSame(oldItem: Product, newItem: Product) =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: Product,
                    newItem: Product,
                ) = oldItem == newItem
            }
    }
}