package com.example.cardapplication.data.firebase

import android.util.Log
import com.example.cardapplication.data.firebase.profile.ProfileManagement
import com.example.cardapplication.data.firebase.models.Card
import com.example.cardapplication.data.firebase.models.PersonalAccount
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private const val FirebaseName = "Accounts"

class PersonalAccountsManager {
    companion object {
        private var mAccount : PersonalAccount = PersonalAccount("", "")
        val account : PersonalAccount get() = mAccount
        private val mCards = mutableListOf<Card>()
        val cards get() = mCards
        private val database = Firebase.database
        private val myRef = database.getReference(FirebaseName)

        fun saveUserInfo(userID : String, account : PersonalAccount) {
            myRef.child(userID).setValue(account)
        }

        fun getUserInfo(userID : String, callBack : DataManager) {

            myRef.child(userID).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var name = ""

                    snapshot.children.forEach {
                        when (it.key) {
                            "name" -> name = it.value.toString()
                            "card" -> Log.i("personal", "Card founded! key = ${it.key}, child = ${it.children}")
                        }
                    }
                    mAccount = PersonalAccount(name = name, id = userID)
                    callBack.callBackData()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })


        }
        fun getCards(callBack : DataManager) {
            myRef.child(ProfileManagement.getCurrentUserID()).child("cards").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    mCards.clear()


                    snapshot.children.forEach { card ->
                        var number = ""
                        var mmyy = ""
                        var cvv = ""

                        card.children.forEach {
                            when (it.key) {
                                "number" -> number = it.value.toString()
                                "mmyy" -> mmyy = it.value.toString()
                                "cvv" -> cvv = it.value.toString()
                            }
                        }
                        Log.i("test", "adding new card: ${Card(number, mmyy, cvv)}")
                        mCards.add(Card(number = number, mmyy = mmyy, cvv = cvv))
                    }
                    callBack.callBackData()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })




        }

        fun getCards1(callBack : DataManager) {
            myRef.child(ProfileManagement.getCurrentUserID()).child("cards").addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    mCards.clear()
                    var number = ""
                    var mmyy = ""
                    var cvv = ""

                    snapshot.children.forEach {
                        when (it.key) {
                            "number" -> number = it.value.toString()
                            "mmyy" -> mmyy = it.value.toString()
                            "cvv" -> cvv = it.value.toString()
                        }
                    }
                    mCards.add(Card(number = number, mmyy = mmyy, cvv = cvv))
                    Log.i("cards", "added new card! number = $number, mmyy = $mmyy, cvv = $cvv")
                    callBack.callBackData()
                    //Log.i("cards", "previoust child name was = $previousChildName, snapshot = $snapshot, snapshotkey =${snapshot.key}")
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    TODO("Not yet implemented")
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }


            })
        }

        fun addNewCard(card : Card) {
            myRef.child(ProfileManagement.getCurrentUserID()).child("cards").child(card.number).setValue(card)
        }

    }
}