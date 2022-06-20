package com.example.cardapplication.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cardapplication.R
import com.example.cardapplication.data.firebase.PersonalAccountsManager
import com.example.cardapplication.data.firebase.models.Card
import com.example.cardapplication.databinding.FragmentNewCardAddingBinding
import mostafa.ma.saleh.gmail.com.editcredit.EditCredit


class NewCardAddingFragment : Fragment() {
    private lateinit var binding : FragmentNewCardAddingBinding

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewCardAddingBinding.bind(view)

        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.MMYY.doOnTextChanged { text, _, _, _ ->
            //Toast.makeText(context, "MM/YY is changing! text = ${text.toString()}, length = ${text.toString().length}", Toast.LENGTH_SHORT).show()
            if (text.toString().length == 2) {
                Toast.makeText(context, "MM/YY is changing! text = ${text.toString()}, length = ${text.toString().length}", Toast.LENGTH_SHORT).show()
                //binding.MMYY.text.append("/")
                binding.MMYY.setText(binding.MMYY.text.toString() + "/")
            }
        }

        binding.addButton.setOnClickListener {
            if (
                checkCardNumber(binding.cardNumber) &&
                checkMMYY(binding.MMYY.text.toString()) &&
                checkCVV(binding.CVV.text.toString())
            )
            {
                PersonalAccountsManager.addNewCard(Card(binding.cardNumber.text.toString(), binding.MMYY.text.toString(), binding.CVV.text.toString()))
                Toast.makeText(context, "Карту успішно додано!", Toast.LENGTH_SHORT).show()
            }

                //щось невірно, нічо не робимо



        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_card_adding, container, false)
    }

    private fun checkMMYY(expirationDate : String) : Boolean {
        if (expirationDate.length != 5) {
            Toast.makeText(context, "Невірний MM/YY.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun checkCardNumber(cardNumber: EditCredit) : Boolean {
        return if (cardNumber.isCardValid)
            true
        else {
            Toast.makeText(context, "Номер карти невірний!", Toast.LENGTH_SHORT).show()
            false
        }

    }

    private fun checkCVV(CVV : String) : Boolean {
        return if (CVV.length == 3)
            true
        else {
            Toast.makeText(context, "Номер CVV невірний!", Toast.LENGTH_SHORT).show()
            false
        }
    }


}