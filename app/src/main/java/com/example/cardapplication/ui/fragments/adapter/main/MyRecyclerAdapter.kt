package com.example.cardapplication.ui.fragments.adapter.main
import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cardapplication.R
import com.example.cardapplication.data.firebase.DataManager
import com.example.cardapplication.data.firebase.StoreManager
import com.example.cardapplication.data.firebase.models.Product
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*


class MyRecyclerAdapter (private val onClick: (productId : String) -> Unit): RecyclerView.Adapter<MyProductViewHolder>() {
     var products : List<Product> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
//        set(value) {
//            field = value
//        }


//    init {
//        Log.i("test", "initing")
//        val callBack = object : DataManager {
//            override fun callBackData() : Int {
//                amountOfProducts = StoreManager.products.size
//                Log.i("test", "doing size. size = $amountOfProducts")
//                return StoreManager.products.size
//            }
//        }
//
//        StoreManager.getProducts(callBack = callBack)
//    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product, parent, false)
        return MyProductViewHolder(view)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: MyProductViewHolder, position: Int) {
        placeProducts(holder, position)
    }


    override fun getItemCount(): Int = products.size

    private fun placeProducts(holder: MyProductViewHolder, position: Int) {
        Log.i("test", "placeProducts")

        holder.name.text = StoreManager.products[position].name
        holder.description.text = StoreManager.products[position].description
        holder.price.text = StoreManager.products[position].price
        Picasso.get().load(StoreManager.products[position].image).into(holder.image)

        holder.itemView.setOnClickListener {
            onClick(StoreManager.products[position].id)
        }

    }
}