package com.example.cardapplication.data.firebase

import android.util.Log
import com.example.cardapplication.data.firebase.models.Category
import com.example.cardapplication.data.firebase.models.Product
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private const val FirebaseName = "Products"

class StoreManager{




    companion object {


        private val mProducts = mutableListOf<Product>()
        val products get() = mProducts
        private val database = Firebase.database
        private val myRef = database.getReference(FirebaseName)
        private lateinit var mLastProduct : Product
        val lastProduct get() = mLastProduct



        fun getProducts(callBack : DataManager)  {
            Log.i("firebase", "key = ${myRef.child(FirebaseName)}")

            myRef.addValueEventListener(object : ValueEventListener {


                override fun onDataChange(snapshot: DataSnapshot) {
                    mProducts.clear()
                    Log.i("firebase", "started getting data from firebase")
                    var id: String
                    var category : Category = Category.DEFAULT
                    var date  = ""
                    var name  = ""
                    var description = ""
                    var price = "-1.0"
                    var image = ""

                    snapshot.children.forEach { product ->
                        id = product.key.toString()
                        product.children.forEach {
                            when (it.key) {
                                "category" -> category = convertCategory(it.value.toString())
                                "name" -> name = it.value.toString()
                                "description" -> description = it.value.toString()
                                "price" -> price = "Грн." + convertPrice(it.value.toString())
                                "image" -> image = it.value.toString()
                                "date" -> date = it.value.toString()

                            }
                        }

                        Log.i("firebase", "we created new Product: ${Product(category, date, name, description, price, image, id)}")
                        mProducts.add(Product(category, date, name, description, price, image, id))

                    }


                    callBack.callBackData()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.i("firebase", "Failed to read value.", error.toException())
                }

            })






        }

        fun getProductById(productId : String, callBack : DataManager) {

            myRef.child(productId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.i("single", "key = ${snapshot.key}, value = ${snapshot.value}")

                    val id : String = snapshot.key.toString()
                    var category : Category = Category.DEFAULT
                    var date = ""
                    var name = ""
                    var description = ""
                    var price = "-1.0"
                    var image = ""

                    snapshot.children.forEach {
                        when (it.key) {
                            "category" -> category = convertCategory(it.value.toString())
                            "name" -> name = it.value.toString()
                            "description" -> description = it.value.toString()
                            "price" -> price = "Грн " + convertPrice(it.value.toString())
                            "image" -> image = it.value.toString()
                            "date" -> date = it.value.toString()

                        }
                    }

                    Log.i("single", "we created new Product: ${Product(category, date, name, description, price, image, id)}")
                    mLastProduct =  Product(category, date, name, description, price, image, id)
                    callBack.callBackData()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.i("test", "cancelled on single value listener")
                }

            })
            //myRef.child(productId).key
            //Log.i("test", "value = ${}, key = ${myRef.child(productId).key}")
            //val tmp = myRef.child(productId).get().result
            //Log.i("test", "value = ${tmp.value}, key = ${tmp.key}")
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



    }



}