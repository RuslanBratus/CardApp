package com.example.cardapplication.ui.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.cardapplication.R
import com.example.cardapplication.databinding.FragmentMainBinding
import com.example.cardapplication.ui.fragments.adapter.main.DepthPageTransformer
import com.example.cardapplication.ui.fragments.adapter.main.MainFragmentsAdapter
import com.google.android.material.tabs.TabLayoutMediator

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [mainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    private lateinit var binding : FragmentMainBinding



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //clearNavigationHistory()
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