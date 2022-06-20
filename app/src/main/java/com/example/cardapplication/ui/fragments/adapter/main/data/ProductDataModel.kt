package com.example.cardapplication.ui.fragments.adapter.main.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cardapplication.data.firebase.models.Product

open class ProductDataModel : ViewModel() {
    val product : MutableLiveData<Product> by lazy {
        MutableLiveData<Product>()
    }
}