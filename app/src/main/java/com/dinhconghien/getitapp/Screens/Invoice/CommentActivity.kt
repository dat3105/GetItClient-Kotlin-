package com.dinhconghien.getitapp.Screens.Invoice

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
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
import kotlinx.android.synthetic.main.activity_comment.*
import kotlinx.android.synthetic.main.activity_invoice_accepted_detail_.*
import kotlinx.android.synthetic.main.item_invoice_accepted.*
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class CommentActivity : AppCompatActivity() {
    var idBill = ""
    var idLap = ""
    var avaLap = ""
    var nameBrand = ""
    var priceLap = 0L
    var amountInCart = 0
    var sumPrice = ""
    var addressOrder = ""
    var nameLap = ""
    var listLapOrder = ArrayList<ListLaptop>()
    val DB_COMMENTLAP =  FirebaseDatabase.getInstance().getReference("commentLap")
    val DB_BILL =  FirebaseDatabase.getInstance().getReference("bill")
    val DB_LAP =  FirebaseDatabase.getInstance().getReference("laptop")
    val DB_USER =  FirebaseDatabase.getInstance().getReference("user")
    var idUser = ""
    val TAG_GETUSER = "DbError_GetUser_CommentActivity"
    val TAG_UPDATELAP = "DbError_UpdateLap_CommentActivity"
    val TAG_UPDATEBILL = "DbError_UpdateBill_CommentActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        setSupportActionBar(toolbar_comment_activity)
        init()
        toolbar_comment_activity.setNavigationOnClickListener {
            val intent = Intent(this,InvoiceAcceptedDetail_Activity::class.java)
            intent.putExtra("idBill",idBill)
            intent.putExtra("sumPrice",addressOrder)
            intent.putExtra("addressOrder",addressOrder)
            startActivity(intent)
            finish()
        }

        btn_confirm_invoiceAcceptedDetail.setOnClickListener {
            val dialogLoading = DialogLoading(this)
            dialogLoading.show()
            try {
                sendComment()
                updateStatusBill()
                val intent = Intent(this,InvoiceAcceptedDetail_Activity::class.java)
                intent.putExtra("idBill",idBill)
                intent.putExtra("sumPrice",addressOrder)
                intent.putExtra("addressOrder",addressOrder)
                dialogLoading.dismiss()
                CustomToast.makeText(this,"Đánh giá thành công",Toast.LENGTH_LONG,1)?.show()
                startActivity(intent)
                finish()
            }catch (e : Exception){
                CustomToast.makeText(this,e.toString(),Toast.LENGTH_LONG,1)?.show()
                dialogLoading.dismiss()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun init(){
        idBill = intent.getStringExtra("idBill")
        idLap = intent.getStringExtra("idLap")
        avaLap = intent.getStringExtra("avaLap")
        nameBrand = intent.getStringExtra("nameBrand")
        nameLap = intent.getStringExtra("nameLap")
        addressOrder = intent.getStringExtra("addressOrder")
        sumPrice = intent.getStringExtra("sumPrice")
        priceLap = intent.getLongExtra("priceLap",0L)
        amountInCart = intent.getIntExtra("amountInCart",0)
        val utils = SharePreference_Utils(this)
        idUser = utils.getSession()
        Glide.with(this).load(avaLap).fitCenter().into(imv_avaLap_item_invoiceAccepted)
        tv_nameLapTop_invoiceAccepted_Item.text = nameLap
        tv_nameBrand_invoiceAccepted_Item.text = nameBrand
        tv_priceLap_invoiceAccepted_Item.text = "$priceLap VNĐ"
        tv_amount_invoiceAccepted_Item.text = "x $amountInCart"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUpRatingAndComment(userName : String,avaUser : String){
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
            val idComment = DB_COMMENTLAP.push().key.toString()
            val date = current.format(formatter)
            val comment = ed_comment_itemBillAccepted.text.toString()
            val ratingByUser = ratingBar_invoiceAccepted_Item.rating.toInt()
            val commentLapModel = CommentLap(idComment,idLap,userName,avaUser,comment,date,ratingByUser)
            DB_COMMENTLAP.child(idComment).setValue(commentLapModel)
            updateInfoLap(ratingByUser)
    }

    private fun sendComment(){
        DB_USER.orderByChild("userID").equalTo(idUser).addListenerForSingleValueEvent(object : ValueEventListener{
            @SuppressLint("LongLogTag")
            override fun onCancelled(error: DatabaseError) {
               Log.d(TAG_GETUSER,error.toString())
            }
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {
                for (param in snapshot.children){
                    val userModel = param.getValue(User::class.java)!!
                    val userName = userModel.userName!!
                    val avaUser = userModel.avaUser!!
                    setUpRatingAndComment(userName,avaUser)
                }
            }
        })
    }

    private fun updateInfoLap(ratingByUser : Int){
        DB_LAP.orderByChild("idLap").equalTo(idLap).addListenerForSingleValueEvent(object : ValueEventListener{
            @SuppressLint("LongLogTag")
            override fun onCancelled(error: DatabaseError) {
              Log.d(TAG_UPDATELAP,error.toString())
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                for (param in snapshot.children){
                    val lapModel = param.getValue(ListLaptop::class.java)!!
                    val rating = lapModel.rating
                    val amountRating = lapModel.amountRating
                    val amountRatingAfterUpdate = amountRating +1
                    val ratingAfterUpdate = (rating*amountRating+ratingByUser) / amountRatingAfterUpdate
                    DB_LAP.child(idLap).child("rating").setValue(ratingAfterUpdate)
                    DB_LAP.child(idLap).child("amountRating").setValue(amountRatingAfterUpdate)
                }
            }
        })
    }

    private fun updateStatusBill(){
        DB_BILL.orderByChild("idBill").equalTo(idBill).addListenerForSingleValueEvent(object : ValueEventListener{
            @SuppressLint("LongLogTag")
            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG_UPDATEBILL,error.toString())
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                 for (param in snapshot.children){
                     val billModel = param.getValue(Bill::class.java)!!
                     listLapOrder = billModel.listLapOrder
                     for (i in 0 until listLapOrder.size){
                         if (listLapOrder[i].idLap == idLap){
                             listLapOrder[i].wasRated = true
                             DB_BILL.child(idBill).child("listLapOrder").setValue(listLapOrder)
                             break
                         }
                     }
                 }
            }
        })
    }

}