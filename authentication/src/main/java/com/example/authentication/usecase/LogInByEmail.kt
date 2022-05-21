package com.example.authentication.usecase

import android.app.Activity
import android.util.Log
import com.example.authentication.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInByEmail {
    companion object AuthenticationHelper{
        private lateinit var auth: FirebaseAuth
        //private val auth: FirebaseAuth = Firebase.auth

        fun checkCurrentUserAuthentication() : Boolean {

            auth = Firebase.auth
            val currentUser = auth.currentUser
            if (currentUser != null){
                Log.d("problems", "Success!")
                return true}
            Log.d("problems", "Fail!")
            return false
        }

        fun logIn(user : User) {
            auth.signInWithEmailAndPassword(user.email, user.password)
                //Activity () can make problems
                .addOnCompleteListener(Activity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("logIn", "signInWithEmail:success")
                        val currentUser = auth.currentUser
                        //updateUI(currentUser)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d("logIn", "signInWithEmail:failure", task.exception)
//                            Toast.makeText(baseContext, "Authentication failed.",
//                                Toast.LENGTH_SHORT).show()
                        //updateUI(null)
                    }
                }
        }



    }
}