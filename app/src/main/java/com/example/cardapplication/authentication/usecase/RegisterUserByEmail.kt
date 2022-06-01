package com.example.cardapplication.authentication.usecase

import android.app.Activity
import android.util.Log
import com.example.cardapplication.authentication.usecase.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterUserByEmail {

    companion object AuthenticationHelper{
        private val auth: FirebaseAuth = Firebase.auth


        fun registerNewUser(user : User) {

            auth.createUserWithEmailAndPassword(user.email, user.password)

                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Authentication", "createUserWithEmail:success")
                        val user = auth.currentUser
                        //updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Authentication", "createUserWithEmail:failure", task.exception)
//                            Toast.makeText(baseContext, "Authentication failed.",
//                                Toast.LENGTH_SHORT).show()
//                            updateUI(null)
                    }
                }
        }



    }
}