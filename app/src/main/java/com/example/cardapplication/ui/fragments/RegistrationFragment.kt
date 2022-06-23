package com.example.cardapplication.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cardapplication.R
import com.example.cardapplication.authentication.usecase.LogInByEmail
import com.example.cardapplication.authentication.usecase.RegisterUserByEmail
import com.example.cardapplication.authentication.usecase.models.User
import com.example.cardapplication.databinding.FragmentRegistrationBinding
import java.util.regex.Pattern


private val PASSWORD_PATTERN = Pattern.compile(
    "^" +
            "(?=.*[0-9])" +         //at least 1 digit
            //"(?=.*[a-z])" +         //at least 1 lower case letter
            //"(?=.*[A-Z])" +         //at least 1 upper case letter
            "(?=.*[a-zA-Z])" +  //any letter
            //"(?=.*[@#$%^&+=])" +  //at least 1 special character
            "(?=\\S+$)" +  //no white spaces
            ".{6,}" +  //at least 6 characters
            "$"
)

class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    private lateinit var binding: FragmentRegistrationBinding



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegistrationBinding.bind(view)
        Log.d("Navigation", "Opening Registration Layout")

        val changeRegistrantsColor = SpannableString(resources.getString(R.string.HaveAccountAlreadyLogin))
        changeRegistrantsColor.setSpan(
            ForegroundColorSpan(Color.parseColor("#408AD2")),
            14,
            23,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        ) // устанавливаем цвет слову Заруєструватися

        binding.HaveAccount.text = changeRegistrantsColor

        binding.HaveAccount.setOnClickListener { setLoginLayout() }


        binding.registrationEmailAddress.addTextChangedListener(MyTextWatcher(binding.registrationEmailAddress))
        binding.registrationPassword.addTextChangedListener(MyTextWatcher(binding.registrationPassword))
        binding.registrationConfirmPassword.addTextChangedListener(MyTextWatcher(binding.registrationConfirmPassword))

        binding.RegistrationButton.setOnClickListener {

            val email : String = binding.registrationEmailAddress.text.toString()
            val password : String = binding.registrationPassword.text.toString()

            if (validateEmailAddress() && validatePassword() && confirmPassword())
                Toast.makeText(context, "Все ок, реєструємо", Toast.LENGTH_SHORT).show()
                RegisterUserByEmail.registerNewUser (this, User(email = email, password = password))
        }


    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }



    private fun setLoginLayout() {
        findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
    }




    private fun validateEmailAddress(email : String = binding.registrationEmailAddress.text.toString()) : Boolean {
        return if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.registrationEmailLayout.error = getString(R.string.error_email)
            true
        } else {
            binding.registrationEmailLayout.isErrorEnabled = false
            false
        }
    }

    private fun validatePassword(password : String = binding.registrationPassword.text.toString()) : Boolean {
        if (password.isEmpty() || !PASSWORD_PATTERN.matcher(password).matches()) {
            binding.registrationPasswordLayout.error = getString(R.string.error_registration_password)
            return false
        }
        else {
            binding.registrationPasswordLayout.isErrorEnabled = false
        }
        return true
    }

    private fun confirmPassword(password : String = binding.registrationPassword.text.toString(), confirmPassword : String = binding.registrationConfirmPassword.text.toString()) : Boolean {
        if (password != confirmPassword) {
            binding.registrationConfirmPasswordLayout.error = getString(R.string.error_confirm_passwords)
            return false
        }
        else {
            binding.registrationConfirmPasswordLayout.isErrorEnabled = false
        }
        return true
    }

    private inner class MyTextWatcher(private val view: View) : TextWatcher {

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun afterTextChanged(editable: Editable) {
            when (view.id) {
                R.id.registrationEmailAddress -> validateEmailAddress()
                R.id.registrationPassword -> validatePassword()
                R.id.registrationConfirmPassword -> confirmPassword()

            }
        }

    }


}