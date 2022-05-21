package com.example.cardapplication

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.authentication.usecase.LogInByEmail

class MainActivity : AppCompatActivity() {
    //private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //private val auth: FirebaseAuth = Firebase.auth


//            auth = Firebase.auth
//            val currentUser = auth.currentUser
//            if (currentUser != null) {
//                Log.d("problems", "Success!")
//                setContentView(R.layout.registration)
//            }
//        else
//            {
//                Log.d("problems", "Fail!")
//                setContentView(R.layout.login)
//            }



        //CHANGE LATER! if true == Shop Store. else - login
        if (LogInByEmail.checkCurrentUserAuthentication())
            setContentView(R.layout.registration)
            //put here setContentView(R.layout.SHOP STORE LAYOUT NAME )
        else
            setContentView(R.layout.login)


        Log.d("problems", "28 line")

        val changeRegistrateColor = SpannableString(resources.getString(R.string.NoAccountRegisterByEmail))
        changeRegistrateColor.setSpan(
            ForegroundColorSpan(Color.parseColor("#408AD2")),
            15,
            29,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        ) // устанавливаем цвет слову Hello
        val registerTextView = findViewById<View>(R.id.HaventAccountRegisterClickable) as TextView
        registerTextView.text = changeRegistrateColor
        //LOGIN VIEW

        registerTextView.setOnClickListener {
            setContentView(R.layout.registration)
            val changeLoginColor = SpannableString(resources.getString(R.string.HaveAccountAlreadyLogin))
            changeLoginColor.setSpan(
                ForegroundColorSpan(Color.parseColor("#408AD2")),
                15,
                23,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE
            ) // устанавливаем цвет слову Hello
            val loginTextView = findViewById<View>(R.id.HaveAccount) as TextView
            registerTextView.text = changeLoginColor

            loginTextView.setOnClickListener {
                setContentView(R.layout.login)
            }
        }


        //LOGIN VIEW






    }
}