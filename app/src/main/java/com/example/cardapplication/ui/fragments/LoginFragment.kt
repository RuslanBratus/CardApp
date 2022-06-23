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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cardapplication.R
import com.example.cardapplication.authentication.usecase.LogInByEmail
import com.example.cardapplication.authentication.usecase.models.User
import com.example.cardapplication.databinding.FragmentLoginBinding
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

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding : FragmentLoginBinding

//    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //ProfileManagement.logOut()

        if (LogInByEmail.checkCurrentUserAuthentication())
            setMainFragment()

        binding = FragmentLoginBinding.bind(view)



        Log.d("Navigation", "Opening Login Layout")




        val changeRegistrantsColor = SpannableString(resources.getString(R.string.NoAccountRegisterByEmail))
        changeRegistrantsColor.setSpan(
            ForegroundColorSpan(Color.parseColor("#408AD2")),
            15,
            29,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        ) // устанавливаем цвет слову Заруєструватися

        binding.HaventAccountRegisterClickable.text = changeRegistrantsColor

        binding.HaventAccountRegisterClickable.setOnClickListener { setRegistrationLayout() }

        binding.loginEmailAddress.addTextChangedListener(MyTextWatcher(binding.loginEmailAddress))
        binding.loginPassword.addTextChangedListener(MyTextWatcher(binding.loginPassword))

        binding.loginButton.setOnClickListener {
            val email : String = binding.loginEmailAddress.text.toString()
            val password : String = binding.loginPassword.text.toString()
            if (validateEmail(email) && validatePassword(password))
                LogInByEmail.logIn(this, User(email = email, password = password))
        }




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    private fun setRegistrationLayout() {
        findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
    }

    private fun setMainFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
    }

    private fun validateEmail(email : String = binding.loginEmailAddress.text.toString()) : Boolean {
        return if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailLayout.error = getString(R.string.error_email)
            false
        } else {
            binding.emailLayout.isErrorEnabled = false;
            true
        }
    }

    private fun validatePassword(password : String = binding.loginPassword.text.toString()) : Boolean {
        return if (password.isEmpty() || !PASSWORD_PATTERN.matcher(password).matches()) {
            //Toast.makeText(context, "Невірний формат паролю.\nПароль має складатись принаймі з 6 символів, 1 цифри та без пробілів.", Toast.LENGTH_SHORT).show()
            binding.passwordLayout.error = getString(R.string.error_login_password)
            false
        }
        else
        {
            binding.passwordLayout.isErrorEnabled = false;
            true
        }
    }


    private inner class MyTextWatcher(private val view: View) : TextWatcher {

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun afterTextChanged(editable: Editable) {
            when (view.id) {
                R.id.loginEmailAddress -> validateEmail()
                R.id.loginPassword -> validatePassword()
            }
        }

    }




}