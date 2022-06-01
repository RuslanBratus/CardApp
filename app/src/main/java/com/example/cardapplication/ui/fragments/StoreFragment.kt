package com.example.cardapplication.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.cardapplication.R
import com.example.cardapplication.authentication.usecase.LogInByEmail
import com.example.cardapplication.authentication.usecase.models.User
import com.example.cardapplication.databinding.FragmentLoginBinding
import com.example.cardapplication.databinding.FragmentStoreBinding
import com.example.cardapplication.ui.fragments.adapter.MainFragmentsAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StoreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StoreFragment : Fragment() {
    private lateinit var binding : FragmentStoreBinding
    private lateinit var pager : ViewPager2


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentStoreBinding.bind(view)

        pager = binding.pager
        val pagerAdapter = MainFragmentsAdapter(this)
        pager.adapter = pagerAdapter

        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                when (state) {
                    ViewPager2.SCROLL_STATE_IDLE -> {
                        Toast.makeText(context, "Current SCROLL_STATE_IDLE 0", Toast.LENGTH_LONG).show()
                        //setCurrentPage()
                    }
                    ViewPager2.SCROLL_STATE_DRAGGING -> {
                        Toast.makeText(context, "Current SCROLL_STATE_DRAGGING 0", Toast.LENGTH_LONG).show()
                    }
                    ViewPager2.SCROLL_STATE_SETTLING -> {
                        Toast.makeText(context, "Current SCROLL_STATE_SETTLING 0", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

    }

    private fun setCurrentPage() {
        if (pager.currentItem == 0) {
            Toast.makeText(context, "Current item 0", Toast.LENGTH_LONG).show()
        }
        else
            Toast.makeText(context, "Current item is NOT 0", Toast.LENGTH_LONG).show()
    }

//    override fun onBackPressed() {
//        if (pager.currentItem == 0) {
//            // If the user is currently looking at the first step, allow the system to handle the
//            // Back button. This calls finish() on this activity and pops the back stack.
//            onBackPressed()
//            pager.currentItem = 1
//
//        } else {
//            // Otherwise, select the previous step.
//            pager.currentItem = pager.currentItem - 1
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_store, container, false)
    }





}