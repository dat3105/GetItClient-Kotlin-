package com.dinhconghien.getitapp.Screens.Cart


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dinhconghien.getitapp.Adapter.Cart_Adapter
import com.dinhconghien.getitapp.Models.BrandLapName
import com.dinhconghien.getitapp.Models.Cart
import com.dinhconghien.getitapp.Models.ListLaptop
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.UI.DialogLoading
import com.dinhconghien.getitapp.Utils.SharePreference_Utils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CartActivity : AppCompatActivity() {
    var listCart = ArrayList<Cart>()
    var listLapOrder = ArrayList<ListLaptop>()
    private lateinit var adapterCart: Cart_Adapter
    private val TAG = "Check size of listCart"
    val DB_CART = FirebaseDatabase.getInstance().getReference("cart")
    var idUser = ""
    val TAG_GETCART = "DbErrorGetCart_CartScreen"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        setSupportActionBar(toolbar_cartScreen)
        val util = SharePreference_Utils(this)
        idUser = util.getSession()
        adapterCart = Cart_Adapter(this,listLapOrder)
        getCart()
        GlobalScope.launch(Dispatchers.Main) {   updateUI() }

        btn_confirm_cartScreen.setOnClickListener {
            val intent = Intent(this,PaymentActivity::class.java)
            startActivity(intent)
        }

        swipeRL_cartScreen.setOnRefreshListener {
            swipeRL_cartScreen.isRefreshing = false
            GlobalScope.launch(Dispatchers.Main) {   updateUI() }
        }

    }

    suspend fun updateUI(){
        val dialogLoading = DialogLoading(this)
        dialogLoading.show()
        delay(500L)
        if (listCart.size == 0){
            reLayout_noneProduct_cartScreen.visibility = View.VISIBLE
            linearCart_hasCartScreen_cartScreen.visibility = View.GONE
            dialogLoading.dismiss()
        }else{
            reLayout_noneProduct_cartScreen.visibility = View.GONE
            linearCart_hasCartScreen_cartScreen.visibility = View.VISIBLE
            getCartItem()
            dialogLoading.dismiss()
        }
    }

    private fun getCart(){
        DB_CART.orderByChild("idUser").equalTo(idUser).addValueEventListener(object : ValueEventListener{
            @SuppressLint("LongLogTag")
            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG_GETCART,error.toString())
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                listCart.clear()
                listLapOrder.clear()
                getCartModel(snapshot)
            }

        })
    }

    private fun getCartModel(snapshot: DataSnapshot){
        for (param in snapshot.children){
            val cartModel = param.getValue(Cart::class.java)
            if (cartModel != null) {
                listCart.add(cartModel)
                listLapOrder = cartModel.listLapOrder
                adapterCart.setListCart(listLapOrder)
            }
        }
    }

    private fun getCartItem() {
        adapterCart = Cart_Adapter(this, listLapOrder)
        rcView_listCart_cartScreen.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rcView_listCart_cartScreen.setHasFixedSize(true)
        rcView_listCart_cartScreen.adapter = adapterCart
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart ${listCart.size}")
    }

}


