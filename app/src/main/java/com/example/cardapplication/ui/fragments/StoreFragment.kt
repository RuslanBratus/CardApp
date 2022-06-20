package com.example.cardapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cardapplication.R
import com.example.cardapplication.ui.fragments.adapter.main.MyRecyclerAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cardapplication.data.firebase.DataManager
import com.example.cardapplication.data.firebase.StoreManager
import com.example.cardapplication.databinding.FragmentStoreBinding
import com.example.cardapplication.ui.fragments.adapter.main.data.ProductDataModel

class StoreFragment : Fragment() {
    private lateinit var binding : FragmentStoreBinding
    private lateinit var mAdapter: MyRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentStoreBinding.bind(view)

        mAdapter = MyRecyclerAdapter {
            val bundle = Bundle()
            bundle.putString("productId", it)
            findNavController().navigate(R.id.action_mainFragment_to_productBuyingFragment, bundle)
        }



        binding.recyclerView.adapter = mAdapter
        //Maybe swap 38 || 36?

        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        setAdapterProducts()

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