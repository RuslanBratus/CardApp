package com.example.cardapplication.data.firebase

import android.util.Log
import com.example.cardapplication.data.firebase.models.*
import com.example.cardapplication.data.firebase.profile.ProfileManagement
import com.google.firebase.auth.ktx.auth
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
        private val mPurchases = mutableListOf<Purchase>()
        val purchases get() = mPurchases
        private val database = Firebase.database
        private val myRef = database.getReference(FirebaseName)




            fun saveUserInfo(userID : String, account : PersonalAccount) {
            myRef.child(userID).setValue(account)
        }

        fun savePurchaseInfo(purchaseId : String, purchase : Purchase ) {
            myRef.child(Firebase.auth.currentUser!!.uid).child("purchases").child(purchaseId).setValue(purchase)
        }



        fun getUserInfo(userID : String, callBack : DataManager) {

            myRef.child(userID).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var name = ""

                    snapshot.children.forEach {
                        when (it.key) {
                            "name" -> name = it.value.toString()
                        }
                    }
                    mAccount = PersonalAccount(name = name, id = userID)
                    callBack.callBackData()
                }

                override fun onCancelled(error: DatabaseError) {
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


        fun getPurchases(callBack : DataManager) {
            myRef.child(ProfileManagement.getCurrentUserID()).child("purchases").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    mPurchases.clear()

                    snapshot.children.forEach { purchase ->
                        var amount = ""
                        var date = ""
                        var id = ""
                        var totalPrice = ""
                        var category : Category = Category.DEFAULT
                        var name = ""
                        var description = ""
                        var price = "-1.0"
                        var image = ""
                        var purchaseDate = ""

                        purchase.children.forEach { child ->
                            Log.i("test", "key = ${child.key}, value = ${child.value}")
                            when (child.key) {
                                "amount" -> amount = child.value.toString()
                                "purchaseDate" -> purchaseDate = child.value.toString()
                                "id" -> id = child.value.toString()
                                "totalPrice" -> totalPrice = child.value.toString()
                                "product" -> child.children.forEach {
                                    when(it.key) {
                                        "category" -> category = convertCategory(it.value.toString())
                                        "name" -> name = it.value.toString()
                                        "description" -> description = it.value.toString()
                                        "price" -> price = "Грн." + convertPrice(it.value.toString())
                                        "image" -> image = it.value.toString()
                                        "date" -> date = it.value.toString()
                                    }
                                }
                            }
                        }
                        val tempPurchase = Purchase(
                            Product(category, date, name, description, price, image, id),
                            totalPrice = totalPrice.toDouble(),
                            amount = amount.toInt(),
                            purchaseDate = purchaseDate,
                            id = id
                        )

                        Log.i("test", "adding new purchase: $tempPurchase")
                        mPurchases.add(tempPurchase)
                    }
                    callBack.callBackData()
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })




        }

        private fun convertCategory(category : String) : Category {
            when (category) {
                "clothes" -> return Category.CLOTHES
                "car" -> return Category.CAR
                "food" -> return Category.FOOD
            }
            return Category.DEFAULT
        }

        private fun convertPrice(price : String) : Double {

            if (!price.contains(",") && !price.contains("."))
                return price.toInt().toDouble()

            if (price.contains(","))
                price.replace(",", ".")


            try {
                return price.toDouble()
            }
            catch (e : Exception) {


                var newPrice = ""

                price.forEach {
                    if (it == ',')
                        newPrice += '.'
                    else if (it.isDigit() || it == '.')
                        newPrice += it

                }
                return try {
                    newPrice.toDouble()
                } catch (i : Exception) {
                    -1.0
                }

            }



        }




        fun addNewCard(card : Card) {
            myRef.child(ProfileManagement.getCurrentUserID()).child("cards").child(card.number).setValue(card)
        }


    }
}