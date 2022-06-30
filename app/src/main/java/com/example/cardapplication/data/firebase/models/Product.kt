package com.example.cardapplication.data.firebase.models


import java.io.Serializable

enum class Category {
    CLOTHES, CAR, FOOD, DEFAULT
}


data class Product (
    val category: Category,
    val date: String,
    val name: String,
    val description: String,
    val price: String,
    val image: String,
    val id: String
) : Serializable