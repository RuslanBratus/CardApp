package com.example.cardapplication.authentication.usecase

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.cardapplication.R
import com.example.cardapplication.authentication.usecase.models.User
import com.example.cardapplication.data.firebase.PersonalAccountsManager
import com.example.cardapplication.data.firebase.models.PersonalAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterUserByEmail {

    companion object AuthenticationHelper{
        private val auth: FirebaseAuth = Firebase.auth


        fun registerNewUser(currentFragment : Fragment, user : User) {


            auth.createUserWithEmailAndPassword(user.email, user.password)

                .addOnCompleteListener { task ->
                    Log.i("registration", "current user before = ${auth.currentUser}")
                    if (task.isSuccessful) {

                        val userName = user.email.substringBefore('@')
                        PersonalAccountsManager.saveUserInfo(task.result.user?.uid!!, PersonalAccount(userName, task.result.user?.uid!!))
                        if (LogInByEmail.checkCurrentUserAuthentication())
                            setMainFragment(currentFragment)
                    } else {
                    }
                }
        }

        private fun setMainFragment(currentFragment: Fragment) {
            NavHostFragment.findNavController(fragment = currentFragment).navigate(R.id.mainFragment)

        }



    }
}