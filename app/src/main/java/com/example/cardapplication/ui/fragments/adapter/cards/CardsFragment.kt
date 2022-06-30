package com.example.cardapplication.ui.fragments.adapter.cards

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardapplication.R
import com.example.cardapplication.data.firebase.DataManager
import com.example.cardapplication.data.firebase.PersonalAccountsManager
import com.example.cardapplication.data.firebase.StoreManager
import com.example.cardapplication.databinding.FragmentCardsBinding

class CardsFragment : Fragment() {
    private lateinit var binding : FragmentCardsBinding
    private lateinit var mAdapter: MyCardsRecyclerAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)






        binding = FragmentCardsBinding.bind(view)

        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }


        if (!this::mAdapter.isInitialized) {
            mAdapter = MyCardsRecyclerAdapter()
            setAdapterCards()
        } else {
            if (StoreManager.products.isEmpty()) {
                setAdapterCards()
            } else {
                mAdapter.cards = PersonalAccountsManager.cards
            }
        }

        binding.cardRecyclerView.adapter = mAdapter
        binding.cardRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.cardRecyclerView.addItemDecoration(mAdapter.getSpacesItemDecoration(10))



        binding.addNewCardButton.setOnClickListener {
//            parentFragmentManager.commit {
//                setCustomAnimations(
//                    R.anim.slide_in,
//                    R.anim.fade_out,
//                    R.anim.fade_in,
//                    R.anim.slide_out
//                )
//            }
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

