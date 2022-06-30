package com.example.cardapplication.ui.fragments.main.adapter

import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cardapplication.R
import com.example.cardapplication.data.firebase.models.Product


class MyRecyclerAdapter (private val onClick: (productId : String) -> Unit): RecyclerView.Adapter<MyProductViewHolder>() {
     var products : List<Product> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product, parent, false)
        return MyProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyProductViewHolder, position: Int) {
        Log.i("test", "onBindViewHolder")
        placeProducts(holder, position)
    }


    override fun getItemCount(): Int = products.size

    private fun placeProducts(holder: MyProductViewHolder, position: Int) {
        Log.i("test", "placeProducts")


        val product = products[position]
        holder.name.text = product.name
        holder.price.text = product.price
        Glide.with(holder.image.context)
            .load(product.image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            onClick(product.id)
        }

    }

}
class SpacesItemDecoration(private val space: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        if (parent.getChildLayoutPosition(view) % 2 == 1 )
            outRect.left = 0
        else
            outRect.left = space




        outRect.right = space
        outRect.bottom = space



        if (parent.getChildLayoutPosition(view) == 0 || parent.getChildLayoutPosition(view) == 1) {
            outRect.top = space
        } else {
            outRect.top = 0
        }
    }
}

