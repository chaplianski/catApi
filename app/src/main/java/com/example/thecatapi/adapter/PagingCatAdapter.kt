package com.example.thecatapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thecatapi.R
import com.example.thecatapi.model.Cat

class PagingCatAdapter(val clickItemListener: (String) -> Unit) : PagingDataAdapter<Cat, PagingCatAdapter.ViewHolder>(DataDifferntiator) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var catImage: ImageView = view.findViewById(R.id.im_item_cat_image)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val catContex = holder.itemView.context
        val cat = getItem(position)

        Glide.with(catContex).load(cat?.url)
            //           .error(R.drawable.ic_baseline_block_24)
            .centerCrop()
            .override(485, 420)
            //           .placeholder(R.drawable.ic_baseline_blur_circular_24)
            .into(holder.catImage)
        holder.itemView.setOnClickListener {
            if (cat != null) {
                clickItemListener(cat.url)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.cat_item_layout, parent, false)
        return ViewHolder(v)
    }

    object DataDifferntiator : DiffUtil.ItemCallback<Cat>() {

        override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
            return oldItem == newItem
        }
    }
}
