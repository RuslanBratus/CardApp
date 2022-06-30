package com.example.cardapplication.data.firebase.models



data class PersonalAccount(
    val name : String,
    val id : String,
    val userCards: MutableList<Card> = mutableListOf()
)
