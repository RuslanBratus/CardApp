package com.example.cardapplication.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
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

        binding.RegistrationButton.setOnClickListener {

            val email : String = binding.registrationEmailAddress.text.toString()
            //Toast.makeText(context, "Емайл = , $email", Toast.LENGTH_SHORT).show()
            val password : String = binding.registrationPassword.text.toString()
            val confirmPassword : String = binding.registrationConfirmPassword.text.toString()


            if (validateEmailAddress(email = email) && validatePassword(password = password, confirmPassword = confirmPassword))
                Toast.makeText(context, "Все ок, реєструємо", Toast.LENGTH_SHORT).show()
                //RegisterUserByEmail.registerNewUser (User(email = email, password = password))
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




    private fun validateEmailAddress(email : String) : Boolean {
        return if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //Toast.makeText(context, "Найс емайл, $email", Toast.LENGTH_SHORT).show()
            true
        } else {
            Toast.makeText(context, "Невірна електронна адреса.", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun validatePassword(password : String, confirmPassword : String) : Boolean {
        if (password != confirmPassword) {
            Toast.makeText(context, "Паролі не збігаються.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.isNotEmpty() && !PASSWORD_PATTERN.matcher(password).matches()) {
            Toast.makeText(context, "Невірний формат паролю.\nПароль має складатись принаймі з 6 символів, 1 цифри та без пробілів.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }


}