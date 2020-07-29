package com.dinhconghien.getitapp.Screens.Invoice

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dinhconghien.getitapp.Adapter.ListLapPayment_Adapter
import com.dinhconghien.getitapp.MainActivity
import com.dinhconghien.getitapp.Models.Bill
import com.dinhconghien.getitapp.Models.Cart
import com.dinhconghien.getitapp.Models.ListLaptop
import com.dinhconghien.getitapp.Models.User
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.UI.CustomToast
import com.dinhconghien.getitapp.UI.DialogLoading
import com.dinhconghien.getitapp.Utils.SharePreference_Utils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_invoice_wating_detail_.*
import kotlinx.android.synthetic.main.activity_list_lap_detail.*
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.coroutines.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class InvoiceWatingDetail_Activity : AppCompatActivity() {
    lateinit var idUser : String
    lateinit var idBill : String
    lateinit var date : String
    lateinit var sumPrice : String
    lateinit var status : String
    lateinit var addressOrder : String
     var listLapOrder = ArrayList<ListLaptop>()
    val DB_BILL =  FirebaseDatabase.getInstance().getReference("bill")
    val DB_USER =  FirebaseDatabase.getInstance().getReference("user")
    private lateinit var adapterLapPayment: ListLapPayment_Adapter
    val TAG_GETUSER = "DbError_getUser_InvoiceWatingDetail"
    val TAG_GETLAPPAYMENT = "DbError_getLapCart_InvoiceWatingDetail"
    lateinit var job: Job


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice_wating_detail_)
        setSupportActionBar(toolbar_invoiceWatingDetail)
        GlobalScope.launch(Dispatchers.Main) { updateUI() }
        swipeRL_billWatingScreen.setOnRefreshListener {
            swipeRL_billWatingScreen.isRefreshing = false
            GlobalScope.launch(Dispatchers.Main) { updateUI() }
        }
        btn_cancel_invoiceWatingDetail.setOnClickListener {
            DB_BILL.child(idBill).child("status").setValue("Đã hủy")
            DB_BILL.child(idBill).child("idPersonCancel").setValue(idUser)
            CustomToast.makeText(this,"Đơn hàng đã bị hủy",Toast.LENGTH_LONG,1)?.show()
            val intet = Intent(this,MainActivity::class.java)
            startActivity(intet)
            finish()
        }
        toolbar_invoiceWatingDetail.setNavigationOnClickListener {
            val intet = Intent(this,MainActivity::class.java)
            startActivity(intet)
            finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

     suspend fun updateUI(){
        val dialogLoading = DialogLoading(this)
        dialogLoading.show()
        delay(500L)
         init()
         setUpUserUI()
         getListLapItem()
         dialogLoading.dismiss()
    }

    @SuppressLint("SetTextI18n")
    private fun init(){
        val utils = SharePreference_Utils(this)
        idUser = utils.getSession()
        idBill = intent.getStringExtra("idBill")
        date =  intent.getStringExtra("date")
        sumPrice =  intent.getStringExtra("sumPrice")
        status = intent.getStringExtra("status")
        addressOrder = intent.getStringExtra("addressOrder")
        job = Job()
        tv_idInvoiceWatingDetail.text = idBill
        tv_address_billWatingDetai.text = addressOrder
        tv_status_invoiceWatingDetail.text = status
        tv_timeOrder_invoiceWatingDetail.text = date
        tv_sumPrice_invoiceWatingDetail.text = sumPrice
    }

    private fun setUpUserUI(){
        DB_USER.orderByChild("userID").equalTo(idUser).addListenerForSingleValueEvent(object :
            ValueEventListener {
            @SuppressLint("LongLogTag")
            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG_GETUSER,error.toString())
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                getUserModel(snapshot)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun getUserModel(snapshot: DataSnapshot){
        for (param in snapshot.children){
            val userModel = param.getValue(User::class.java)
            if (userModel != null){
                tv_userName_billWatingDetai.text = userModel.userName
                tv_phoneNumber_billWatingDetai.text = userModel.phone
                if (userModel.avaUser != ""){
                    Glide.with(this).load(userModel.avaUser).fitCenter().into(imv_avaUser_billWatingDetail)
                }
            }
        }
    }

    private fun getListLapItem(){
        adapterLapPayment = ListLapPayment_Adapter(listLapOrder)
        rcView_listLapWating.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        rcView_listLapWating.adapter = adapterLapPayment
        setListLapOrder()
    }

    private fun setListLapOrder(){
        DB_BILL.orderByChild("idBill").equalTo(idBill).addListenerForSingleValueEvent(object : ValueEventListener{
            @SuppressLint("LongLogTag")
            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG_GETLAPPAYMENT,error.toString())
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                listLapOrder.clear()
                getListLapModel(snapshot)
            }
        })
    }

    private fun getListLapModel(snapshot: DataSnapshot){
        for (param in snapshot.children){
            val billModel = param.getValue(Bill::class.java)
            if (billModel != null){
                listLapOrder = billModel.listLapOrder
                adapterLapPayment.setListLapPaymentNew(listLapOrder)
            }
        }
    }
}