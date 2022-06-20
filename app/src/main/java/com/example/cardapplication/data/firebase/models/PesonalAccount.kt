package com.example.cardapplication.data.firebase.models

//enum class Category {
//    CLOTHES, CAR, FOOD, DEFAULT
//}


data class PersonalAccount(
    val name : String,
    val id : String,
    val userCards: MutableList<Card> = mutableListOf<Card>()
)
