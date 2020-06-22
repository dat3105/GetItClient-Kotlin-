package com.dinhconghien.getitapp.Screens.Cart


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dinhconghien.getitapp.Adapter.Cart_Adapter
import com.dinhconghien.getitapp.Models.Cart
import com.dinhconghien.getitapp.R
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {

    var listCart = ArrayList<Cart>()
    private lateinit var adapterCart: Cart_Adapter
    private lateinit var cart: Cart

    private val TAG = "Check size of listCart"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        setSupportActionBar(toolbar_cartScreen)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        addCartItem()

        Log.d(TAG,"onCreate ${listCart.size}")

        btn_confirm_cartScreen.setOnClickListener {
            val intent = Intent(this,PaymentActivity::class.java)
            startActivity(intent)
        }

    }

   fun addCartItem(){
        cart = Cart(R.drawable.avatarlaptop,"Vostro","Laptop Lenovo", 10000000,10)
        listCart.add(cart)

        cart = Cart(R.drawable.laptopdell_slider,"Pavillon","Laptop HP", 10000000,10)
        listCart.add(cart)

        cart = Cart(R.drawable.hinhnena,"Mac Pro retina","Laptop Mac", 10000000,10)
        listCart.add(cart)
    }

    private fun getCartItem() {
        adapterCart = Cart_Adapter(this, listCart)
        rcView_listCart_cartScreen.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rcView_listCart_cartScreen.setHasFixedSize(true)
        rcView_listCart_cartScreen.adapter = adapterCart
    }

    override fun onStart() {
        super.onStart()
        getCartItem()
        Log.d(TAG,"onStart ${listCart.size}")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResume is being called ${listCart.size}")
    }
}


