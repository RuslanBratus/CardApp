package com.example.cardapplication.ui.fragments.main.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cardapplication.R

class MyProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val name : TextView = itemView.findViewById(R.id.product_name)
    val image : ImageView = itemView.findViewById(R.id.product_image)
    val price : TextView = itemView.findViewById(R.id.product_price)

}