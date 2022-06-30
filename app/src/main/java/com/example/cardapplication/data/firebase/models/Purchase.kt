package com.example.cardapplication.data.firebase.models


data class Purchase (
    val product : Product,
    val totalPrice : Double,
    val amount : Int,
    val purchaseDate : String,
    val id : String
)
