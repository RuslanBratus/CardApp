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

class StoreManager(){




    companion object {


        private val mProducts = mutableListOf<Product>()
        val products get() = mProducts
        private val database = Firebase.database
        private val myRef = database.getReference(FirebaseName)



        fun getProducts(callBack : DataManager)  {
            Log.i("firebase", "key = ${myRef.child(FirebaseName)}")

            myRef.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    mProducts.clear()
                    Log.i("firebase", "started getting data from firebase")
                    var id : String = ""
                    var category : Category = Category.DEFAULT
                    var date : String = ""
                    var name : String = ""
                    var description : String = ""
                    var price : String = "-1.0"
                    var image : String = ""

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
                        val tmp = Product(category, date, name, description, price, image, id)
                        Log.i("firebase", "we created new Product: $tmp")
                        mProducts.add(Product(category, date, name, description, price, image, id))

                    }


                    callBack.callBackData()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.i("firebase", "Failed to read value.", error.toException())
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