package com.example.cardapplication.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cardapplication.R
import com.example.cardapplication.data.firebase.DataManager
import com.example.cardapplication.data.firebase.PersonalAccountsManager
import com.example.cardapplication.databinding.FragmentCardsBinding
import com.example.cardapplication.ui.fragments.adapter.cards.MyCardsRecyclerAdapter
import com.example.cardapplication.ui.fragments.adapter.main.MyRecyclerAdapter

class CardsFragment : Fragment() {
    private lateinit var binding : FragmentCardsBinding
    private lateinit var mAdapter: MyCardsRecyclerAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("test", "onViewCreated onViewCreated onViewCreated")
        binding = FragmentCardsBinding.bind(view)
        mAdapter = MyCardsRecyclerAdapter()

        setAdapterCards()

        binding.cardRecyclerView.adapter = mAdapter
        binding.cardRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.cardRecyclerView.addItemDecoration(mAdapter.getSpacesItemDecoration(10))



        binding.addNewCard.setOnClickListener {
            findNavController().navigate(R.id.action_cardsFragment_to_newCardAddingFragment)
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cards, container, false)
    }


    private fun setAdapterCards() {
        val callBack = object : DataManager {
            override fun callBackData() {
                mAdapter.cards = PersonalAccountsManager.cards
            }
        }
        PersonalAccountsManager.getCards(callBack = callBack)
    }

}

