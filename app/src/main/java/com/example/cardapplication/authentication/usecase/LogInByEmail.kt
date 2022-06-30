package com.example.cardapplication.authentication.usecase

import android.util.Log
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
                        setMainFragment(currentFragment = currentFragment)
                    } else {
                        Log.d("logIn", "signInWithEmail:failure", task.exception)
                    }
                }
        }

        private fun setMainFragment(currentFragment: Fragment) {

            findNavController(fragment = currentFragment).navigate(R.id.mainFragment)

        }






    }
}