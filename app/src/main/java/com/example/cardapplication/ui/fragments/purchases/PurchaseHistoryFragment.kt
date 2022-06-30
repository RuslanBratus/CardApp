package com.example.cardapplication.ui.fragments.purchases

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cardapplication.R
import com.example.cardapplication.data.firebase.DataManager
import com.example.cardapplication.data.firebase.PersonalAccountsManager
import com.example.cardapplication.data.firebase.StoreManager
import com.example.cardapplication.databinding.FragmentPurchaseHistoryBinding
import com.example.cardapplication.ui.fragments.main.adapter.MyRecyclerAdapter
import com.example.cardapplication.ui.fragments.main.adapter.SpacesItemDecoration
import com.example.cardapplication.ui.fragments.purchases.adapter.MyPurchasesAdapter

class PurchaseHistoryFragment : Fragment() {
    private lateinit var binding: FragmentPurchaseHistoryBinding
    private lateinit var mAdapter : MyPurchasesAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPurchaseHistoryBinding.bind(view)

        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }

        if (!this::mAdapter.isInitialized) {
            mAdapter = MyPurchasesAdapter()
            setAdapterPurchases()
        } else {
            if (PersonalAccountsManager.purchases.isEmpty()) {
                setAdapterPurchases()
            } else {
                mAdapter.purchases = PersonalAccountsManager.purchases
            }
        }

        binding.purchaseHistoryRecyclerView.adapter = mAdapter
        binding.purchaseHistoryRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.purchaseHistoryRecyclerView.addItemDecoration(mAdapter.getSpacesItemDecoration(20))

    }

    private fun setAdapterPurchases() {
        val callBack = object : DataManager {
            override fun callBackData()  {
                mAdapter.purchases = PersonalAccountsManager.purchases
            }
        }

        PersonalAccountsManager.getPurchases(callBack = callBack)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_purchase_history, container, false)
    }


}