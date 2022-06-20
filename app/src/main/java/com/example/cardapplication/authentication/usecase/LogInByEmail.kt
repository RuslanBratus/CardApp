package com.example.cardapplication.authentication.usecase

import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.cardapplication.R
import com.example.cardapplication.authentication.usecase.models.User
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


        fun logIn(currentFragment: Fragment, user : User) {
            if (checkCurrentUserAuthentication()) {
                setMainFragment(currentFragment)
            }
            auth = Firebase.auth
            auth.signInWithEmailAndPassword(user.email, user.password)

                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("logIn", "signInWithEmail:success")
                        val currentUser = auth.currentUser
                        setMainFragment(currentFragment = currentFragment)
                        //findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
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

        private fun setMainFragment(currentFragment: Fragment) {

            findNavController(fragment = currentFragment).navigate(R.id.mainFragment)
            //findNavController(fragment = currentFragment).

        }





    }
}