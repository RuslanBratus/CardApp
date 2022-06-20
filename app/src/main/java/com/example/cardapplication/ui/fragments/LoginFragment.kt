package com.example.cardapplication.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
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

        binding.loginButton.setOnClickListener {
            val email : String = binding.loginEmailAddress.text.toString()
            val password : String = binding.loginPassword.text.toString()
            if (email == "1" && password == "1") {
                //@TODO DELETE THIS
                Toast.makeText(context, "Loggined", Toast.LENGTH_LONG).show()
                LogInByEmail.logIn(this, User(email = email, password = password))

            }
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

    private fun validateEmail(email : String) : Boolean {
        return if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //Toast.makeText(context, "Найс емайл, $email", Toast.LENGTH_SHORT).show()
            true
        } else {
            Toast.makeText(context, "Невірна електронна адреса.", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun validatePassword(password : String) : Boolean {
        if (password.isNotEmpty() && !PASSWORD_PATTERN.matcher(password).matches()) {
            Toast.makeText(context, "Невірний формат паролю.\nПароль має складатись принаймі з 6 символів, 1 цифри та без пробілів.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }


    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        //@TODO CHANGE NUMBER
        override fun getItemCount(): Int = 5

        override fun createFragment(position: Int): Fragment = LoginFragment()

        //ScreenSlidePageFragment()
    }




}