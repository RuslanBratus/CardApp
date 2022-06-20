package com.example.cardapplication.ui.fragments.adapter.main

import android.media.Image
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cardapplication.R

class MyProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val name : TextView = itemView.findViewById(R.id.product_name)
    val description : TextView = itemView.findViewById(R.id.product_description)
    val image : ImageView = itemView.findViewById(R.id.product_image)
    val price : TextView = itemView.findViewById(R.id.product_price)

}