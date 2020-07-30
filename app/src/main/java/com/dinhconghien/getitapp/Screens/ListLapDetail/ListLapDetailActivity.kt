package com.dinhconghien.getitapp.Screens.ListLapDetail

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dinhconghien.getitapp.Adapter.ListLaptop_Adapter
import com.dinhconghien.getitapp.MainActivity
import com.dinhconghien.getitapp.Models.BrandLapName
import com.dinhconghien.getitapp.Models.Cart
import com.dinhconghien.getitapp.Models.LaptopDetail
import com.dinhconghien.getitapp.Models.ListLaptop
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.Screens.ListLaptop.ListLaptopActivity
import com.dinhconghien.getitapp.UI.CustomToast
import com.dinhconghien.getitapp.UI.DialogLoading
import com.dinhconghien.getitapp.Utils.SharePreference_Utils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_list_lap_detail.*
import kotlinx.coroutines.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ListLapDetailActivity : AppCompatActivity() {
    var idLap = ""
    var idBrandLap = ""
    var priceLap = ""
    var amountRating = 0
    var quantity = 0
    val DB_LAPDETAIL = FirebaseDatabase.getInstance().getReference("laptopDetail")
    val DB_BRANDLAP = FirebaseDatabase.getInstance().getReference("brandLap")
    val DB_LAPTOP = FirebaseDatabase.getInstance().getReference("laptop")
    val DB_CART = FirebaseDatabase.getInstance().getReference("cart")
    val TAG_GETLAPDETAIL = "DbErrorGETLAPDETAIL"
    val TAG_GETBRAND = "DbErrorGETBrandDETAIL"
    val TAG_ERROR_GETLAP = "DbErrorGetLapDETAIL"
    val TAG_CART_ERROR = "DbErrorGetCartDETAIL"
    var listLapCart = ArrayList<ListLaptop>()
    private var dsLap = ArrayList<ListLaptop>()
    private lateinit var adapterListLap: ListLaptop_Adapter
    lateinit var job: Job
    lateinit var userID : String
    var listLapCheckCart = ArrayList<ListLaptop>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_lap_detail)
        setSupportActionBar(toolbar_listLapDetailScreen)
        job = Job()
        idLap = intent.getStringExtra("idLap")
        idBrandLap = intent.getStringExtra("idBrandLap")
        val utils = SharePreference_Utils(this)
        userID = utils.getSession()
        GlobalScope.launch(Dispatchers.Main) {
            initUI()
        }

        getListLapForCheck()

        GlobalScope.launch(Dispatchers.IO) {
            setUpListLap()
        }

        toolbar_listLapDetailScreen.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        swipeRL_listLapDetail.setOnRefreshListener {
            swipeRL_listLapDetail.isRefreshing = false
            GlobalScope.launch(Dispatchers.Main) {
                initUI()
            }
        }

        btn_watchMore_listLapDetail.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                val dialogLoading = DialogLoading(this@ListLapDetailActivity)
                dialogLoading.show()
                delay(500L)
                showCustomDialog()
                dialogLoading.dismiss()
            }
        }

        tv_watchMore_listLapDetailScreen.setOnClickListener {
            val intent = Intent(this, ListLaptopActivity::class.java)
            intent.putExtra("idBrandLap", idBrandLap)
            startActivity(intent)
            finish()
        }

        imv_addToCart_listLapDetailScreen.setOnClickListener {
            addToCart()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun showCustomDialog() {
        val viewGroup = findViewById<ViewGroup>(android.R.id.content)
        //then we will inflate the custom alert dialog xml that we created
        val dialogView =
            LayoutInflater.from(this).inflate(R.layout.dialog_infolaptop_detail, viewGroup, false)
        //Now we need an AlertDialog.Builder object
        val builder = AlertDialog.Builder(this)
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView)
        //finally creating the alert dialog and displaying it
        val alertDialog = builder.create()
        val imv_back = dialogView.findViewById<ImageView>(R.id.imv_back_dialogInfoLapDetail)
        val tv_boXuLi: TextView = dialogView.findViewById(R.id.tv_kindOfCPU_infoLapDetail_dialog)
        val tv_congNgheCPU: TextView = dialogView.findViewById(R.id.tv_techCPU_infoLapDetail_dialog)
        val tv_speedCPU: TextView = dialogView.findViewById(R.id.tv_speedCPU_infoLapDetail_dialog)
        val tv_boNhoDem: TextView = dialogView.findViewById(R.id.tv_boNhoDem_infoLapDetail_dialog)
        val tv_dungLuongRAM: TextView =
            dialogView.findViewById(R.id.tv_ramCapicity_infoLapDetail_dialog)
        val tv_loaiRAM: TextView = dialogView.findViewById(R.id.tv_ramType_infoLapDetail_dialog)
        val tv_tocDoBUS: TextView = dialogView.findViewById(R.id.tv_busSpeed_infoLapDetail_dialog)
        val tv_soLuongKhe: TextView =
            dialogView.findViewById(R.id.tv_holeOfRam_infoLapDetail_dialog)
        val tv_xuatXu: TextView = dialogView.findViewById(R.id.tv_xuatXu_infoLapDetail_dialog)
        val tv_namSanXuat: TextView =
            dialogView.findViewById(R.id.tv_productionYear_infoLapDetail_dialog)

        //initUI for lapDetail
        DB_LAPDETAIL.orderByChild("idLap").equalTo(idLap)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG_GETLAPDETAIL, error.toString())
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (param in snapshot.children) {
                        val lapDetailModel = param.getValue(LaptopDetail::class.java)!!
                        tv_boXuLi.text = lapDetailModel.hangCPU
                        tv_congNgheCPU.text = lapDetailModel.congNgheCPU
                        tv_boNhoDem.text = lapDetailModel.boNhoDem
                        tv_dungLuongRAM.text = lapDetailModel.dungLuongRAM
                        tv_loaiRAM.text = lapDetailModel.loaiRAM
                        tv_soLuongKhe.text = lapDetailModel.soLuongRAM
                        tv_speedCPU.text = lapDetailModel.tocDoCPU
                        tv_namSanXuat.text = lapDetailModel.namSanXuat
                        tv_tocDoBUS.text = lapDetailModel.tocDoBus
                        tv_xuatXu.text = lapDetailModel.xuatXu
                    }
                }
            })
        imv_back.setOnClickListener { alertDialog.dismiss() }
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()
    }

    @SuppressLint("SetTextI18n")
    suspend fun initUI() {
        val dialogLoading = DialogLoading(this)
        dialogLoading.show()
        delay(500L)
        setUpLapUI()
        setUpNameBrandUI()
        setUpLapDetailUI()
        getLapItem()
        dialogLoading.dismiss()
    }

    private fun setUpLapUI() {
        DB_LAPTOP.orderByChild("idLap").equalTo(idLap)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG_ERROR_GETLAP, error.toString())
                }

                @SuppressLint("SetTextI18n")
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (param in snapshot.children) {
                        val lapModel = param.getValue(ListLaptop::class.java)!!
                        priceLap = lapModel.priceLap.toString()
                        quantity = lapModel.quantity
                        if (priceLap.length == 7) {
                            val firstChar = priceLap.substring(0, 1)
                            val middleChar = priceLap.substring(1, 4)
                            val lastChar = priceLap.substring(4, 7)
                            priceLap = "$firstChar.$middleChar.$lastChar"
                        } else if (priceLap.length == 8) {
                            val firstChar = priceLap.substring(0, 2)
                            val middleChar = priceLap.substring(2, 5)
                            val lastChar = priceLap.substring(5, 8)
                            priceLap = "$firstChar.$middleChar.$lastChar"
                        }
                        tv_nameLaptop_listLapDetailScreen.text = lapModel.nameLap
                        tv_priceLaptop_listLapDetailScreen.text = "$priceLap VNĐ"
                        if (quantity == 0){
                            tv_amountLaptop_listLapDetailScreen.text = "Tạm hết hàng"
                        }
                        else{
                            tv_amountLaptop_listLapDetailScreen.text = "Số lượng : ${lapModel.quantity}"
                        }
                        tv_amountSellLaptop_listLapDetailScreen.text =
                            "Đã bán : ${lapModel.amountSell}"
                        ratingBar_listLapDetail.rating = lapModel.rating.toFloat()
                        amountRating = lapModel.amountRating
                        if (amountRating == 0) {
                            tv_amountRating_listLapDetailScreen.text = "(Chưa có đánh giá)"
                        }
                        tv_amountRating_listLapDetailScreen.text = "($amountRating lượt đánh giá)"
                        Glide.with(this@ListLapDetailActivity).load(lapModel.avaLap).fitCenter()
                            .into(imv_avaLap_listLapDetail)
                    }
                }

            })
    }

    private fun setUpLapDetailUI() {
        DB_LAPDETAIL.orderByChild("idLap").equalTo(idLap)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG_GETLAPDETAIL, error.toString())
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (param in snapshot.children) {
                        val lapDetailModel = param.getValue(LaptopDetail::class.java)!!
                        tv_cpuTech_listLapDetailScreen.text = lapDetailModel.congNgheCPU
                        tv_cpuSpeed_listLapDetailScreen.text = lapDetailModel.tocDoCPU
                        tv_bo_listLapDetailScreen.text = lapDetailModel.boNhoDem
                    }
                }
            })
    }

    private fun setUpNameBrandUI() {
        DB_BRANDLAP.orderByChild("idBrandLap").equalTo(idBrandLap)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG_GETBRAND, error.toString())
                }

                @SuppressLint("DefaultLocale")
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (param in snapshot.children) {
                        val brandModel = param.getValue(BrandLapName::class.java)!!
                        tv_nameBrand_listLapDetailScreen.text = brandModel.nameBrand.toUpperCase()
                    }
                }

            })
    }

    private fun getLapItem() {
        adapterListLap = ListLaptop_Adapter(this, dsLap)
        val gridLayoutManager =
            GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
        rcView_sameType_listLapDetailScreen.layoutManager = gridLayoutManager
        rcView_sameType_listLapDetailScreen.setHasFixedSize(true)
        getLap()
        rcView_sameType_listLapDetailScreen.adapter = adapterListLap
    }

    private fun getLap() {
        idBrandLap = intent.getStringExtra("idBrandLap")
        DB_LAPTOP.orderByChild("idBrandLap").equalTo(idBrandLap)
            .addValueEventListener(object : ValueEventListener {

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG_ERROR_GETLAP, error.toString())
                }
                override fun onDataChange(snapshot: DataSnapshot) {
                    dsLap.clear()
                    getLapModel(snapshot)
                    adapterListLap.setListLapTop(dsLap)
                }

            })
    }

    private fun getLapModel(snapshot: DataSnapshot) {
        for (param in snapshot.children) {
            val lapModel = param.getValue(ListLaptop::class.java)
            dsLap.add(lapModel!!)
        }
    }

    private fun addToCart(){
        val dialogLoading = DialogLoading(this)
        dialogLoading.show()
        if (quantity == 0){
            CustomToast.makeText(this,"Sản phẩm đang tạm thời hết hàng.Vui lòng chọn sản phẩm khác"
                ,Toast.LENGTH_LONG,3)?.show()
            dialogLoading.dismiss()
        }
        else{
            getListLapForCheck()
            val noti = checkIDLapForCart()
            if (noti == "Sản phẩm đã có trong giỏ hàng"){
                CustomToast.makeText(this,noti,Toast.LENGTH_LONG,3)?.show()
                dialogLoading.dismiss()
            }
            else{
                listLapCheckCart.addAll(listLapCart)
                val cartModel = Cart(userID,"",listLapCheckCart)
                DB_CART.child(userID).setValue(cartModel)
                CustomToast.makeText(this,noti,Toast.LENGTH_LONG,1)?.show()
                dialogLoading.dismiss()
            }
        }

    }

    private fun checkIDLapForCart() : String{
        var noti = "Thêm sản phẩm thành công"
        for (i in 0 until listLapCheckCart.size){
            if(listLapCheckCart[i].idLap.equals(idLap)){
                noti = "Sản phẩm đã có trong giỏ hàng"
            }
        }
        return noti
    }

    private fun getLapModelForCart(snapshot: DataSnapshot){
        for(param in snapshot.children){
            val lapModel = param.getValue(ListLaptop::class.java)!!
            listLapCart.add(lapModel)
        }
    }

    private fun getListLapForCheck(){
        DB_CART.orderByChild("idUser").equalTo(userID).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG_CART_ERROR,error.toString())
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                listLapCheckCart.clear()
                getLapModelForCheck(snapshot)
            }
        })
    }

    private fun getLapModelForCheck(snapshot: DataSnapshot){
        for (param in snapshot.children){
            val cartModel = param.getValue(Cart::class.java)
            if (cartModel != null) {
                listLapCheckCart = cartModel.listLapOrder
            }
        }
    }

    //set up list lap to add into cart if necessrary
    private fun setUpListLap(){
        DB_LAPTOP.orderByChild("idLap").equalTo(idLap).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG_ERROR_GETLAP,error.toString())
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                listLapCart.clear()
                getLapModelForCart(snapshot)
            }
        })
    }

}