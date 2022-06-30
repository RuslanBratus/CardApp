package com.example.cardapplication.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cardapplication.R
import com.example.cardapplication.data.firebase.PersonalAccountsManager
import com.example.cardapplication.data.firebase.models.Card
import com.example.cardapplication.databinding.FragmentNewCardAddingBinding
import mostafa.ma.saleh.gmail.com.editcredit.EditCredit


class NewCardAddingFragment : Fragment() {
    private lateinit var binding : FragmentNewCardAddingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewCardAddingBinding.bind(view)

        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }




        binding.cardNumber.addTextChangedListener(MyTextWatcher(binding.cardNumber))
        binding.MMYY.addTextChangedListener(MyTextWatcher(binding.MMYY))
        binding.CVV.addTextChangedListener(MyTextWatcher(binding.CVV))

        binding.addButton.setOnClickListener {
            if (
                checkCardNumber() &&
                checkMMYY() &&
                checkCVV()
            )
            {
                PersonalAccountsManager.addNewCard(Card(binding.cardNumber.text.toString(), binding.MMYY.text.toString(), binding.CVV.text.toString()))
                Toast.makeText(context, getString(R.string.card_added), Toast.LENGTH_SHORT).show()
            }



        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_card_adding, container, false)
    }

    @SuppressLint("SetTextI18n")
    private fun checkMMYY(expirationDate : String = binding.MMYY.text.toString()) : Boolean {

        if (expirationDate.length == 2) {
            binding.MMYY.setText(binding.MMYY.text.toString() + "/")
        }


        if (expirationDate.length != 5) {
            binding.MMYY.error = getString(R.string.error_incorrect_MMYY)
            return false
        }

        binding.CVVLayout.isErrorEnabled = false;
        return true
    }

    private fun checkCardNumber(cardNumber: EditCredit = binding.cardNumber) : Boolean {
        return if (cardNumber.isCardValid) {
            binding.cardNumberLayout.isErrorEnabled = false;
            true
        }
        else {
            binding.cardNumberLayout.error = getString(R.string.error_incorrect_card_number)
            false
        }

    }

    private fun checkCVV(CVV : String = binding.CVV.text.toString()) : Boolean {
        return if (CVV.length == 3) {
            binding.CVVLayout.isErrorEnabled = false;
            true
        }
        else {
            binding.CVV.error = getString(R.string.error_incorrect_CVV)
            false
        }
    }


    private inner class MyTextWatcher(private val view: View) : TextWatcher {

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun afterTextChanged(editable: Editable) {
            when (view.id) {
                R.id.cardNumber -> checkCardNumber()
                R.id.MM_YY -> checkMMYY()
                R.id.CVV -> checkCVV()
            }
        }

    }


}