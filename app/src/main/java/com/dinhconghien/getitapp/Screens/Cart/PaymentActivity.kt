package com.dinhconghien.getitapp.Screens.Cart

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dinhconghien.getitapp.Adapter.ListLapPayment_Adapter
import com.dinhconghien.getitapp.MainActivity
import com.dinhconghien.getitapp.Models.*
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.UI.DialogLoading
import com.dinhconghien.getitapp.Utils.SharePreference_Utils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PaymentActivity : AppCompatActivity() {
    var listLapPayment = ArrayList<ListLaptop>()
    private lateinit var adapterLapPayment: ListLapPayment_Adapter
    val DB_CART = FirebaseDatabase.getInstance().getReference("cart")
    val DB_USER =  FirebaseDatabase.getInstance().getReference("user")
    val DB_BILL =  FirebaseDatabase.getInstance().getReference("bill")
    val TAG_GETUSER = "DbError_getUser_paymentScreen"
    val TAG_GETCART = "DbError_getCart_paymentScreen"
    val TAG_GETLAPPAYMENT = "DbError_getLapCart_paymentScreen"
    var idUser = ""
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        setSupportActionBar(toolbar_paymentScreen)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        GlobalScope.launch(Dispatchers.Main) {
            updateUI()
        }

        swipeRL_paymentScreen.setOnRefreshListener {
            swipeRL_paymentScreen.isRefreshing = false
            GlobalScope.launch(Dispatchers.Main) {
                updateUI()
            }
        }

        btn_confirm_paymentScreen.setOnClickListener {
            val idBill = DB_BILL.push().key.toString()
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
            var date = current.format(formatter)
            val sumPrice = tv_sumPrice_paymentScreen.text.toString()
            val status = "Đang chờ xác nhận"
            val addressOrder = tv_address_paymentScreen.text.toString()
            val billModel = Bill(idBill,idUser,date,sumPrice,status,addressOrder,listLapPayment)
            DB_BILL.child(idBill).setValue(billModel)
            DB_CART.child(idUser).removeValue()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    suspend fun updateUI(){
        val utils = SharePreference_Utils(this)
        idUser = utils.getSession()
        val dialogLoading = DialogLoading(this)
        dialogLoading.show()
        delay(400L)
        setUpUserUI()
        getListLapItem()
        dialogLoading.dismiss()
    }

    private fun setListLapPayment(){
        DB_CART.orderByChild("idUser").equalTo(idUser).addListenerForSingleValueEvent(object : ValueEventListener{
            @SuppressLint("LongLogTag")
            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG_GETLAPPAYMENT,error.toString())
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                listLapPayment.clear()
                getListLapModel(snapshot)
            }
        })
    }

    private fun getListLapModel(snapshot: DataSnapshot){
        for (param in snapshot.children){
            val cartModel = param.getValue(Cart::class.java)
            if (cartModel != null){
                listLapPayment = cartModel.listLapOrder
                adapterLapPayment.setListLapPaymentNew(listLapPayment)
                var sumPrice = 0

                for (i in 0 until listLapPayment.size){
                    sumPrice += listLapPayment[i].priceLap * listLapPayment[i].amountInCart
                }
                var price = sumPrice.toString()
                if (price.length == 7) {
                    val firstChar = price.substring(0, 1)
                    val middleChar = price.substring(1, 4)
                    val lastChar = price.substring(4, 7)
                    price = "$firstChar.$middleChar.$lastChar"
                } else if (price.length == 8) {
                    val firstChar = price.substring(0, 2)
                    val middleChar = price.substring(2, 5)
                    val lastChar = price.substring(5, 8)
                    price = "$firstChar.$middleChar.$lastChar"
                }
                else if (price.length == 9) {
                    val firstChar = price.substring(0, 3)
                    val middleChar = price.substring(3, 6)
                    val lastChar = price.substring(6, 9)
                    price = "$firstChar.$middleChar.$lastChar"
                }
                else if (price.length == 10) {
                    val firstChar = price.substring(0, 1)
                    val middleChar = price.substring(1, 4)
                    val preLastChar = price.substring(4, 7)
                    val lastChar = price.substring(7, 10)
                    price = "$firstChar.$middleChar.$preLastChar.$lastChar"
                }
                else if (price.length == 11) {
                    val firstChar = price.substring(0, 2)
                    val middleChar = price.substring(2, 5)
                    val preLastChar = price.substring(5, 8)
                    val lastChar = price.substring(8, 11)
                    price = "$firstChar.$middleChar.$preLastChar.$lastChar"
                }
                tv_sumPrice_paymentScreen.text = "$price VNĐ"
            }
        }
    }

    private fun setUpUserUI(){
        DB_USER.orderByChild("userID").equalTo(idUser).addListenerForSingleValueEvent(object : ValueEventListener{
            @SuppressLint("LongLogTag")
            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG_GETUSER,error.toString())
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                getUserModel(snapshot)
            }
        })
        DB_CART.orderByChild("idUser").equalTo(idUser).addListenerForSingleValueEvent(object : ValueEventListener{
            @SuppressLint("LongLogTag")
            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG_GETCART,error.toString())
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                getCartModel(snapshot)
            }

        })
    }

    private fun getCartModel(snapshot: DataSnapshot){
        for (param in snapshot.children){
            val cartModel = param.getValue(Cart::class.java)
            if (cartModel != null){
                tv_address_paymentScreen.text = cartModel.addressOrder
            }
        }
    }

    private fun getUserModel(snapshot: DataSnapshot){
        for (param in snapshot.children){
            val userModel = param.getValue(User::class.java)
            if (userModel != null){
                tv_userName_paymentScreen.text = userModel.userName
                tv_phoneNumber_paymentScreen.text = userModel.phone
                if (userModel.avaUser != ""){
                    Glide.with(this).load(userModel.avaUser).fitCenter().into(imv_avaUser_paymentScreen)
                }
            }
        }
    }

    private fun getListLapItem(){
        adapterLapPayment = ListLapPayment_Adapter(listLapPayment)
        rcView_listItem_paymentScreen.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rcView_listItem_paymentScreen.adapter = adapterLapPayment
        setListLapPayment()
    }

}