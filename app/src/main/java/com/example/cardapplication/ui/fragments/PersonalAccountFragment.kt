package com.example.cardapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cardapplication.R
import com.example.cardapplication.data.firebase.profile.ProfileManagement
import com.example.cardapplication.data.firebase.DataManager
import com.example.cardapplication.data.firebase.PersonalAccountsManager
import com.example.cardapplication.databinding.FragmentPersonalAccountBinding
import kotlinx.coroutines.runBlocking

class PersonalAccountFragment : Fragment() {
    private lateinit var binding : FragmentPersonalAccountBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPersonalAccountBinding.bind(view)

        binding.cardImage.setOnClickListener {
            setMyCardsFragment()
        }
        placeAccountInfo()


    }

    private fun placeAccountInfo() = runBlocking{
        val callBack = object : DataManager {
            override fun callBackData()  {
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

}