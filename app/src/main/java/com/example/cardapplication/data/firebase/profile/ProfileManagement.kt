package com.example.cardapplication.data.firebase.profile

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileManagement() {


    companion object {
        private val auth = Firebase.auth

        fun logOut() {
            auth.signOut()
        }

        fun getCurrentUserID() = auth.currentUser!!.uid


    }
}