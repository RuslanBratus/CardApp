package com.example.cardapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cardapplication.R
import com.example.cardapplication.data.firebase.DataManager
import com.example.cardapplication.data.firebase.StoreManager
import com.example.cardapplication.databinding.FragmentStoreBinding
import com.example.cardapplication.ui.fragments.main.adapter.MyRecyclerAdapter
import com.example.cardapplication.ui.fragments.main.adapter.SpacesItemDecoration


class StoreFragment : Fragment() {
    private lateinit var binding : FragmentStoreBinding
    private lateinit var mAdapter: MyRecyclerAdapter



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentStoreBinding.bind(view)




        if (!this::mAdapter.isInitialized) {
            mAdapter = MyRecyclerAdapter {
                val bundle = Bundle()
                bundle.putString("productId", it)
                findNavController().navigate(R.id.action_mainFragment_to_confirmBuyingProductFragment, bundle)
            }

            setAdapterProducts()
        } else {
            if (StoreManager.products.isEmpty()) {
                setAdapterProducts()
            } else {
                mAdapter.products = StoreManager.products
            }
        }

        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerView.addItemDecoration( SpacesItemDecoration(30))
    }




        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

            return inflater.inflate(R.layout.fragment_store, container, false)
        }

    private fun setAdapterProducts() {
        val callBack = object : DataManager {
            override fun callBackData()  {
                mAdapter.products = StoreManager.products
            }
        }

        Log.i("test", "before get Products")
        StoreManager.getProducts(callBack = callBack)
    }










}