package com.dinhconghien.getitapp.Screens.Invoice

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dinhconghien.getitapp.Adapter.BillAccepted_Adapter
import com.dinhconghien.getitapp.MainActivity
import com.dinhconghien.getitapp.Models.Bill
import com.dinhconghien.getitapp.Models.CommentLap
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
import kotlinx.android.synthetic.main.activity_invoice_accepted_detail_.*
import kotlinx.coroutines.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class InvoiceAcceptedDetail_Activity : AppCompatActivity() {
    lateinit var idUser : String
    lateinit var idBill : String
    lateinit var sumPrice : String
    lateinit var addressOrder : String
    var avaUser = ""
    var listLapOrder = ArrayList<ListLaptop>()
    val DB_BILL =  FirebaseDatabase.getInstance().getReference("bill")
    val DB_USER =  FirebaseDatabase.getInstance().getReference("user")
    val DB_COMMENTLAP =  FirebaseDatabase.getInstance().getReference("commentLap")
    val DB_LAP =  FirebaseDatabase.getInstance().getReference("laptop")
    private lateinit var adapterLapPayment: BillAccepted_Adapter
    val TAG_GETUSER = "DbError_getUser_InvoiceWatingDetail"
    val TAG_GETLAPPAYMENT = "DbError_getLapCart_InvoiceWatingDetail"
    lateinit var job: Job

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice_accepted_detail_)

        setSupportActionBar(toolbar_invoiceAccepted)
        GlobalScope.launch(Dispatchers.Main) { updateUI() }
        swipeRL_billAcceptedDetail.setOnRefreshListener {
            swipeRL_billAcceptedDetail.isRefreshing = false
            GlobalScope.launch(Dispatchers.Main) { updateUI() }
        }
        toolbar_invoiceAccepted.setNavigationOnClickListener {
            val intet = Intent(this, MainActivity::class.java)
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
        sumPrice =  intent.getStringExtra("sumPrice")
        addressOrder = intent.getStringExtra("addressOrder")
        job = Job()
        tv_idInvoice_Accepted.text = "Mã hóa đơn : $idBill"
        tv_address_billAcceptedDetail.text = addressOrder
        tv_sumPrice_billAcceptedDetail.text = sumPrice
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
                avaUser = userModel.avaUser
                tv_userName_billAcceptedDetail.text = userModel.userName
                tv_phoneNumber_billAcceptedDetail.text = userModel.phone
                if (userModel.avaUser != ""){
                    Glide.with(this).load(userModel.avaUser).fitCenter().into(imv_avaUser_billAcceptedDetail)
                }
            }
        }
    }

    private fun getListLapItem(){
        adapterLapPayment = BillAccepted_Adapter(listLapOrder)
        rcView_invoiceAcceptedScreen.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        rcView_invoiceAcceptedScreen.adapter = adapterLapPayment
        setListLapOrder()
        adapterLapPayment.setOnItemClickedListener(object : BillAccepted_Adapter.OnItemClickedListener{
            override fun onClicked(position: Int) {
                if (listLapOrder[position].wasRated == false){
                    val idLap = listLapOrder[position].idLap
                    val avaLap = listLapOrder[position].avaLap
                    val nameBrand = listLapOrder[position].nameBrand
                    val priceLap = listLapOrder[position].priceLap
                    val amountInCart = listLapOrder[position].amountInCart
                    val nameLap =  listLapOrder[position].nameLap
                    val intent = Intent(this@InvoiceAcceptedDetail_Activity,CommentActivity::class.java)
                    intent.putExtra("idLap",idLap)
                    intent.putExtra("avaLap",avaLap)
                    intent.putExtra("nameBrand",nameBrand)
                    intent.putExtra("priceLap",priceLap)
                    intent.putExtra("amountInCart",amountInCart)
                    intent.putExtra("idBill",idBill)
                    intent.putExtra("sumPrice",sumPrice)
                    intent.putExtra("addressOrder",addressOrder)
                    intent.putExtra("nameLap",nameLap)
                    startActivity(intent)
                }
               else{
                    CustomToast.makeText(this@InvoiceAcceptedDetail_Activity,
                        "Bạn đã đánh giá sản phẩm ở đơn hàng này rồi!",Toast.LENGTH_LONG,2)?.show()
                }
            }

        })
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
                adapterLapPayment.setListBillAccep(listLapOrder)
            }
        }
    }


}