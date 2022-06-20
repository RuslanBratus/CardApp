package com.example.cardapplication.ui.fragments.adapter.cards

import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.cardapplication.R
import com.example.cardapplication.data.firebase.DataManager
import com.example.cardapplication.data.firebase.PersonalAccountsManager
import com.example.cardapplication.data.firebase.models.Card
import kotlinx.coroutines.runBlocking

class MyCardsRecyclerAdapter : RecyclerView.Adapter<MyCardViewHolder>() {
    var cards : List<Card> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    fun getSpacesItemDecoration(space : Int = 10) = SpacesItemDecoration(space = space)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card, parent, false)
        return MyCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyCardViewHolder, position: Int) {
        placeCards(holder, position)
    }
    override fun getItemCount(): Int = cards.size

    private fun placeCards(holder: MyCardViewHolder, position: Int) = runBlocking{
        val card = cards[position]
        holder.card.cardNumber = card.number
        holder.card.expiryDate = card.mmyy
    }

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

