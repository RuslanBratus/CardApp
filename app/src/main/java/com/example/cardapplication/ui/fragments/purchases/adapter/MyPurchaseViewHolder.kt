package com.example.cardapplication.ui.fragments.purchases.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cardapplication.R
import com.vinaygaba.creditcardview.CreditCardView

class MyPurchaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val name : TextView = itemView.findViewById(R.id.purchase_product_name)
    val image : ImageView = itemView.findViewById(R.id.purchase_product_image)
    val totalPrice : TextView = itemView.findViewById(R.id.purchase_product_total_price)
    val amount : TextView = itemView.findViewById(R.id.purchase_amount)
    val date : TextView = itemView.findViewById(R.id.purchase_date)


}


