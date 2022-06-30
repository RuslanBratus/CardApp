package com.example.cardapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cardapplication.R
import com.example.cardapplication.data.firebase.DataManager
import com.example.cardapplication.data.firebase.StoreManager
import com.example.cardapplication.data.firebase.models.Product
import com.example.cardapplication.databinding.FragmentProductBuyingBinding

class ProductBuyingFragment : Fragment() {
    private lateinit var binding : FragmentProductBuyingBinding
    private lateinit var currentProduct : Product


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductBuyingBinding.bind(view)

        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buyButton.setOnClickListener {
            //setConfirmBuyFragment()
        }
        //binding.test.text = requireArguments().getString("productId")
        setProduct()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("test", "bundle 29 line before = ${requireArguments().getString("productId")}")
        return inflater.inflate(R.layout.fragment_product_buying, container, false)
    }


    private fun setProduct() {
        val callBack = object : DataManager {
            override fun callBackData()  {
                currentProduct = StoreManager.lastProduct

                binding.productName.text = currentProduct.name
                binding.productDescription.text = currentProduct.description
                binding.productPrice.text = currentProduct.price
                Glide.with(binding.productImage.context)
                    .load(currentProduct.image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.productImage)
            }
        }

        StoreManager.getProductById(requireArguments().getString("productId")!!, callBack)
    }






}