package com.example.cardapplication.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cardapplication.R
import com.example.cardapplication.databinding.FragmentConfirmedPurchaseBinding

class ConfirmedPurchaseFragment : Fragment() {
    private lateinit var binding: FragmentConfirmedPurchaseBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentConfirmedPurchaseBinding.bind(view)

        val changeRegistrantsColor = SpannableString(resources.getString(R.string.purchase_successfully))
        changeRegistrantsColor.setSpan(
            ForegroundColorSpan(Color.parseColor("#408AD2")),
            26,
            resources.getString(R.string.purchase_successfully).length,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        )


        binding.purchaseSuccessfullyText.text = changeRegistrantsColor

        binding.purchaseSuccessfullyText.setOnClickListener {
            findNavController().navigate(R.id.action_confirmedPurchaseFragment_to_mainFragment)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_confirmed_purchase, container, false)
    }



}