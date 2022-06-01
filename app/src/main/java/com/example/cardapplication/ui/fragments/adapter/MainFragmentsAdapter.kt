package com.example.cardapplication.ui.fragments.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cardapplication.ui.fragments.PaymentFragment
import com.example.cardapplication.ui.fragments.StoreFragment

class MainFragmentsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) StoreFragment() else PaymentFragment()
    }


}