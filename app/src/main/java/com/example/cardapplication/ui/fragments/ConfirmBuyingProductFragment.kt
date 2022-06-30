package com.example.cardapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.text.isDigitsOnly
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cardapplication.R
import com.example.cardapplication.data.firebase.DataManager
import com.example.cardapplication.data.firebase.PersonalAccountsManager
import com.example.cardapplication.data.firebase.StoreManager
import com.example.cardapplication.data.firebase.models.Product
import com.example.cardapplication.data.firebase.models.Purchase
import com.example.cardapplication.databinding.FragmentConfirmBuyingProductBinding
import java.util.*

class ConfirmBuyingProductFragment : Fragment() {
    private lateinit var binding: FragmentConfirmBuyingProductBinding
    private lateinit var currentProduct : Product


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentConfirmBuyingProductBinding.bind(view)


        setProduct()

        binding.minusAmount.isClickable = false

        binding.minusAmount.setOnClickListener {
            var amount : Int = binding.productAmount.text.toString().toInt()

            if (amount <= 1) {
                binding.minusAmount.isClickable = false
                return@setOnClickListener
            }

            val totalPrice : String = "Грн " + ((--amount * convertPriceToDouble(currentProduct.price)).toString())
            if (totalPrice.length > 10) {
                binding.totalPrice.text = totalPrice.subSequence(0, 10)
            }
            else
                binding.totalPrice.text = totalPrice

            binding.productAmount.text = (amount.toString())



        }


        binding.plusAmount.setOnClickListener {
            var amount : Int = binding.productAmount.text.toString().toInt()


            val totalPrice : String = "Грн " + ((++amount * convertPriceToDouble(currentProduct.price)).toString())
            if (totalPrice.length > 10) {
                binding.totalPrice.text = totalPrice.subSequence(0, 10)
            }
            else
                binding.totalPrice.text = totalPrice


            binding.productAmount.text = (amount.toString())
            if (amount > 1) {
                binding.minusAmount.isClickable = true
            }
        }


        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }

        //@TODO замінити 3 лістенери в ХМЛ на 1 леяют в якому будть ці 3 обєкти і тоді лістенер тільки для леяута добавити
        setCardsListeners()

        binding.confirmBuying.setOnClickListener {
            val random = Random()

            val totalPrice = convertPriceToDouble(binding.totalPrice.text.toString())
            val amount = binding.productAmount.text.toString().toInt()
            val purchaseId  = totalPrice.hashCode() + amount.hashCode() + currentProduct.hashCode() + random.nextInt()

            if (checkCard()) {
                if (binding.productAmount.text.toString().toInt() < 1) {
                    Toast.makeText(context, "Неможлива кількість товару!", Toast.LENGTH_LONG).show()
                }
                else
                {
                    PersonalAccountsManager.savePurchaseInfo(
                        purchaseId = purchaseId.toString(),
                        Purchase(
                            product = currentProduct,
                            totalPrice = totalPrice,
                            amount = amount,
                            purchaseDate = getCurrentDate(),
                            id = purchaseId.toString()
                        ))

                    findNavController().navigate(R.id.action_confirmBuyingProductFragment_to_confirmedPurchaseFragment)
                }

            }
        }



    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_confirm_buying_product, container, false)
    }

    private fun setProduct() {
        val callBack = object : DataManager {
            override fun callBackData() {
                currentProduct = StoreManager.lastProduct

                binding.productName.text = currentProduct.name
                binding.productDescription.text = currentProduct.description
                binding.productPrice.text = currentProduct.price
                binding.totalPrice.text = currentProduct.price


                if (requireArguments().getStringArrayList("data")?.get(1) != null) {
                    binding.chooseCardText.text = requireArguments().getStringArrayList("data")?.get(1)
                }
                Log.i("test", "cardNumber = ${requireArguments().getStringArrayList("data")?.get(1)}")

                Glide.with(binding.productImage.context)
                    .load(currentProduct.image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.productImage)
            }
        }

        val productId : String = requireArguments().getStringArrayList("data")?.get(0) ?: requireArguments().getString("productId")!!
        Log.i("test", "productId = $productId")

        StoreManager.getProductById(productId, callBack)


    }

    private fun convertPriceToDouble(price : String) : Double {
        return  price.subSequence(4, price.length).toString().toDouble()
    }

    private fun setCardsListeners() {


        binding.chooseCardText.setOnClickListener {
            val bundle = bundleOf()
            bundle.putString("productId", currentProduct.id)
            findNavController().navigate(R.id.action_confirmBuyingProductFragment_to_chooseCardFragment, bundle)
        }
        binding.paymentMethodIcon.setOnClickListener {
            val bundle = bundleOf()
            bundle.putString("productId", currentProduct.id)
            findNavController().navigate(R.id.action_confirmBuyingProductFragment_to_chooseCardFragment, bundle)
        }

        binding.paymentArrow.setOnClickListener {
            val bundle = bundleOf()
            bundle.putString("productId", currentProduct.id)
            findNavController().navigate(R.id.action_confirmBuyingProductFragment_to_chooseCardFragment, bundle)
        }
    }

    private fun checkCard() : Boolean{
        var tmp = binding.chooseCardText.text.toString()


        while (tmp.contains(" ")) {
            tmp = tmp.replace(" ", "")
        }

        return if (tmp.isDigitsOnly()) {
            true
        } else {
            Toast.makeText(context, "Оберіть спосіб оплати", Toast.LENGTH_LONG).show()
            false
        }

    }

    private fun getCurrentDate() : String {
        val time = Calendar.getInstance().time
        return time.toString().subSequence(3, time.toString().length).removeRange(7, 26).toString()
    }


}