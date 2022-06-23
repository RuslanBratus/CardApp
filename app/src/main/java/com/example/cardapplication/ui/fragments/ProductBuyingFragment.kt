package com.example.cardapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.cardapplication.R
import com.example.cardapplication.data.firebase.StoreManager
import com.example.cardapplication.databinding.FragmentProductBuyingBinding

class ProductBuyingFragment : Fragment() {
    private lateinit var binding : FragmentProductBuyingBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductBuyingBinding.bind(view)
        binding.test.text = requireArguments().getString("productId")
        StoreManager.getProductById(requireArguments().getString("productId")!!)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("test", "bundle 29 line before = ${requireArguments().getString("productId")}")
        return inflater.inflate(R.layout.fragment_product_buying, container, false)
    }





}