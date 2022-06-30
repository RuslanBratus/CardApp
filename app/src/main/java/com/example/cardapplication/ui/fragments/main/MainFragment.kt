package com.example.cardapplication.ui.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cardapplication.R
import com.example.cardapplication.databinding.FragmentMainBinding
import com.example.cardapplication.ui.fragments.main.adapter.DepthPageTransformer
import com.example.cardapplication.ui.fragments.main.adapter.MainFragmentsAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {
    private lateinit var binding : FragmentMainBinding



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        val pagerAdapter = MainFragmentsAdapter(this)
        binding.pager.setPageTransformer(DepthPageTransformer())

        binding.pager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.pager) {
            tabLayout, position ->
            when (position) {
                0 -> tabLayout.text = resources.getText(R.string.products)
                1 -> tabLayout.text = resources.getText(R.string.personal_account)
            }

        }.attach()

    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }



}