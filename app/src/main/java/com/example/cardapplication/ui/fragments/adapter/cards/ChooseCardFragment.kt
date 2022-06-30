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
import com.example.cardapplication.databinding.FragmentChooseCardBinding

class ChooseCardFragment : Fragment() {
    private lateinit var binding: FragmentChooseCardBinding
    private lateinit var mAdapter: MyCardsRecyclerAdapter
    private lateinit var productId : String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChooseCardBinding.bind(view)
        productId = requireArguments().getString("productId")!!

        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }


        if (!this::mAdapter.isInitialized) {
            mAdapter = MyCardsRecyclerAdapter {
                val bundle = Bundle()
                bundle.putStringArrayList("data", arrayListOf(productId, it))
                findNavController().navigate(R.id.action_chooseCardFragment_to_confirmBuyingProductFragment, bundle)
            }
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



    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_choose_card, container, false)
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