package com.example.cardapplication.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cardapplication.R
import com.example.cardapplication.data.firebase.DataManager
import com.example.cardapplication.data.firebase.PersonalAccountsManager
import com.example.cardapplication.data.firebase.profile.ProfileManagement
import com.example.cardapplication.databinding.FragmentPersonalAccountBinding


class PersonalAccountFragment : Fragment() {
    private lateinit var binding : FragmentPersonalAccountBinding
    private lateinit var actionBarDrawerToggle : ActionBarDrawerToggle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentPersonalAccountBinding.bind(view)

        binding.cardImage.setOnClickListener {
            setMyCardsFragment()
        }

        binding.myShoppingIcon.setOnClickListener {
            setMyPurchasesHistoryFragment()
        }

        binding.logoutIcon.setOnClickListener {
            ProfileManagement.logOut()
            findNavController().navigate(R.id.action_personalAccountFragment_to_loginFragment)
        }

        placeAccountInfo()



    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun placeAccountInfo() {
        val callBack = object : DataManager {
            override fun callBackData() {
                binding.userName.text = PersonalAccountsManager.account.name
            }
        }
        PersonalAccountsManager.getUserInfo(
            ProfileManagement.getCurrentUserID(),
            callBack
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_personal_account, container, false)
    }

    private fun setMyCardsFragment() {
        findNavController().navigate(R.id.action_mainFragment_to_cardsFragment)
    }

    private fun setMyPurchasesHistoryFragment() {
        findNavController().navigate(R.id.action_mainFragment_to_purchaseHistoryFragment)
    }

}