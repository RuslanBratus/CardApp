package com.example.cardapplication.ui.fragments.adapter.cards

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cardapplication.R
import com.vinaygaba.creditcardview.CreditCardView




class MyCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var card : CreditCardView = itemView.findViewById(R.id.card)

}