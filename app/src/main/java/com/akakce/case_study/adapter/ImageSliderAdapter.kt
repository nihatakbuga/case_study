package com.akakce.case_study.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akakce.case_study.R
import com.akakce.case_study.data.model.Product
import com.bumptech.glide.Glide
import java.text.NumberFormat
import java.util.Locale

class ImageSliderAdapter(
    private val products: List<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageViewProduct)
        val title: TextView = itemView.findViewById(R.id.textViewTitle)
        val price: TextView = itemView.findViewById(R.id.textViewPrice)
        val rating: TextView = itemView.findViewById(R.id.textViewRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_slider_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val product = products[position]

        Glide.with(holder.itemView.context)
            .load(product.image)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_error)
            .into(holder.image)

        holder.title.text = product.title
        val formatter = NumberFormat.getCurrencyInstance(Locale("tr", "TR"))
        holder.price.text = formatter.format(product.price)
        holder.rating.text = "★ ${product.rating.rate} (${product.rating.count})"

        holder.itemView.setOnClickListener {
            onItemClick(product)
        }
    }

    override fun getItemCount(): Int = products.size
}
