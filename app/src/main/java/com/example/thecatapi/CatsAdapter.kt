package com.example.thecatapi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thecatapi.model.Cat

class CatsAdapter(private val catContext: Context, private val cats: MutableList<Cat>) :
    RecyclerView.Adapter <CatsAdapter.ViewHolder>() {

    private lateinit var cListener: onClickCatListener

    interface onClickCatListener {
        fun onItemClick(position: Int)
    }

    class ViewHolder(itemView: View, listener: onClickCatListener) : RecyclerView.ViewHolder(itemView) {
        var catImage: ImageView = itemView.findViewById(R.id.im_item_cat_image)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cat_item_layout, parent, false)
        return ViewHolder(v, cListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cat: Cat = cats[position]

        Glide.with(catContext).load(cat.url)
            //           .error(R.drawable.ic_baseline_block_24)
            .centerCrop()
            .override(485, 420)
            //           .placeholder(R.drawable.ic_baseline_blur_circular_24)
            .into(holder.catImage)
    }

    override fun getItemCount(): Int {
        return cats.size
    }
}
