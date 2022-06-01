package com.example.cardapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.NavHostFragment
import com.example.cardapplication.authentication.usecase.LogInByEmail
import com.example.cardapplication.ui.fragments.LoginFragment
import com.example.cardapplication.ui.fragments.RegistrationFragment
import com.example.cardapplication.ui.fragments.StoreFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    //private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        navController.navigate(R.id.action_loginFragment_to_storeFragment)
        //navController.navigate(R.id.action_loginFragment_to_registrationFragment)





//        val storeFragment = StoreFragment()
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.nav_host_fragment, storeFragment)
//        fragmentTransaction.commit()

        //TODO
//        Firebase.auth.signOut()
//
//        if (LogInByEmail.checkCurrentUserAuthentication()) {
//            Toast.makeText(this, "Увійшли!", Toast.LENGTH_LONG).show()
//        }
//        else
//            Toast.makeText(this, "НЕ УВІЙШЛИ! ПОМИЛКА!!", Toast.LENGTH_LONG).show()

        //RegisterUserByEmail.registerNewUser (User(email = "ruslan.bratus2@aiesec.net", password = "ruslan"))


        //navController.navigate(R.id.action_loginFragment_to_registrationFragment)
        //navController.navigate(R.id.action_loginFragment_to_registrationFragment)
        //findNavController(R.id.action_loginFragment_to_registrationFragment)



        //navController.navigate(R.id.action_registrationFragment_to_loginFragment)

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


       //TODO
//        if (LogInByEmail.checkCurrentUserAuthentication())
//            setRegistrationLayout()
//            //put here setContentView(R.layout.SHOP STORE LAYOUT NAME )
//        else
//            setLoginLayout()



    }



    fun setLoginLayout() {
        //setContentView(R.layout.login)
        val loginFragment = LoginFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, loginFragment)
        fragmentTransaction.commit()



    }

    fun setRegistrationLayout() {
        val registrationFragment = RegistrationFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, registrationFragment)
        fragmentTransaction.commit()
        }




}