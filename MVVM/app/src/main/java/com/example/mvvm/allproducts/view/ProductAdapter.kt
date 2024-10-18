package com.example.mvvm.allproducts.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvm.R
import com.example.mvvm.model.ProductDTO

class ProductDiffUtil : DiffUtil.ItemCallback<ProductDTO>() {
    override fun areItemsTheSame(oldItem: ProductDTO, newItem: ProductDTO): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductDTO, newItem: ProductDTO): Boolean {
        return oldItem == newItem
    }
}

class ListProductAdapter(private val context: Context, private val listener: SelectProductClickListener) : ListAdapter<ProductDTO, ListProductAdapter.ViewHolder>(
    ProductDiffUtil()
) {


    class ViewHolder(itemView:  View) : RecyclerView.ViewHolder(itemView)
    {
        val title: TextView = itemView.findViewById(R.id.textViewTitle)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val button: View = itemView.findViewById(R.id.btnAddToFavorites)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_row,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentProduct = getItem(position)

            holder.title.text = currentProduct.title
            Glide.with(context)
                .load(currentProduct.thumbnail)
                .into(holder.imageView)

        holder.button.setOnClickListener {
            listener.OnSelect(currentProduct)
        }

        }
}


