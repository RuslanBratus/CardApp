package com.example.cardapplication.ui.fragments.purchases.adapter

import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cardapplication.R
import com.example.cardapplication.data.firebase.models.Purchase

class MyPurchasesAdapter() : RecyclerView.Adapter<MyPurchaseViewHolder>() {
    var purchases : List<Purchase> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun getSpacesItemDecoration(space : Int = 10) = SpacesItemDecoration(space = space)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPurchaseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.purchase, parent, false)
        return MyPurchaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyPurchaseViewHolder, position: Int) {
        placePurchase(holder, position)
    }

    private fun placePurchase(holder: MyPurchaseViewHolder, position: Int) {


        val purchase = purchases[position]
        holder.name.text = purchase.product.name

        holder.totalPrice.text = "Загальна вартість: " + purchase.totalPrice.toString()
        holder.amount.text = "Кількість: " + purchase.amount.toString()
        holder.date.text = purchase.purchaseDate

        Glide.with(holder.image.context)
            .load(purchase.product.image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.image)
    }

    override fun getItemCount(): Int  = purchases.size

    inner class SpacesItemDecoration(private val space: Int = 10) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.bottom = space
        }
    }

}