package com.dinhconghien.getitapp.Screens.Home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dinhconghien.getitapp.Adapter.BrandLapName_Adapter
import com.dinhconghien.getitapp.Adapter.SliderImage_Adapter
import com.dinhconghien.getitapp.Models.*
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.Screens.Cart.CartActivity
import com.dinhconghien.getitapp.UI.CustomToast
import com.dinhconghien.getitapp.UI.DialogLoading
import com.dinhconghien.getitapp.Utils.SharePreference_Utils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    lateinit var sliderView: SliderView
    lateinit var adapterImageSlider: SliderImage_Adapter
    var listImageSlider = ArrayList<SliderImage>()
    lateinit var tv_welcome: TextView
    lateinit var adapterBrandLap: BrandLapName_Adapter
    lateinit var reclerView_brandLap: RecyclerView
    lateinit var swipeRL: SwipeRefreshLayout
    lateinit var tv_amountCart : TextView
    var listBrandName = ArrayList<BrandLapName>()
    lateinit var imv_cart: ImageView
    val TAG = "Check HomeFragment 's Lifecycle"
    val listSliderTAG = "Check list Slider"
    var dbReference = FirebaseDatabase.getInstance().getReference("user")
    val DB_BRANDLAP = FirebaseDatabase.getInstance().getReference("brandLap")
    val DB_CART = FirebaseDatabase.getInstance().getReference("cart")
    var listCart = ArrayList<Cart>()
    var listLapOrder = ArrayList<ListLaptop>()
    lateinit var current_userID : String

    @SuppressLint("LongLogTag")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initComponents(view)
        val utils = SharePreference_Utils(view.context)
        current_userID = utils.getSession()
        getListCart()
        GlobalScope.launch(Dispatchers.Main) { updateUI(view) }
        adapterImageSlider = SliderImage_Adapter(view.context, listImageSlider)
        initForSliderImage()
        Log.d(TAG, "OncreateView is being called !")

        imv_cart.setOnClickListener {
            val intent = Intent(
                view.context,
                CartActivity::class.java
            )
            startActivity(intent)
        }
        dbReference.child(current_userID)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Log.d("DbErrorHome", error.toString())
                }
                @SuppressLint("SetTextI18n")
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    if (user != null) {
                        tv_welcome.text = "Xin chào,${user.userName}"
                    }
                }
            })

        swipeRL.setOnRefreshListener {
            swipeRL.isRefreshing = false
            GlobalScope.launch(Dispatchers.Main) { updateUI(view) }

        }
        return view
    }

    private fun initComponents(view: View) {
        sliderView = view.findViewById(R.id.imageSlider)
        reclerView_brandLap = view.findViewById(R.id.reclerView_itemBrandName_homeScreen)
        imv_cart = view.findViewById(R.id.imv_cart_homeScreen)
        tv_welcome = view.findViewById(R.id.tv_welcome_home)
        swipeRL = view.findViewById(R.id.swipeRL_Home)
        tv_amountCart = view.findViewById(R.id.tv_amountListCart_homeScreen)
    }

    private fun getBrand() {
        DB_BRANDLAP.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.d("DbErrorBrandHome", error.toString())
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                for (param in snapshot.children) {
                    listBrandName.clear()
                    getBrandModel(snapshot)
                    adapterBrandLap.setListBrand(listBrandName)
                    Log.v("Check size listBrand", "${listBrandName.size}")

                }
            }
        })
    }


    fun getBrandModel(snapShot: DataSnapshot) {
        for (param in snapShot.children) {
            var brandModel = param.getValue(BrandLapName::class.java)!!
            listBrandName.add(brandModel)
        }
    }

    private fun initForSliderImage() {
        sliderView.setSliderAdapter(adapterImageSlider)
        sliderView.setIndicatorAnimation(IndicatorAnimationType.SWAP)
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        sliderView.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT
        sliderView.indicatorUnselectedColor = Color.GRAY
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()
    }

    suspend fun updateUI(view: View) {
        val dialogLoading = DialogLoading(view.context)
        dialogLoading.show()
        delay(800L)
        getBrandNameItem(view)
        checkListCart()
        dialogLoading.dismiss()
    }

    private fun getBrandNameItem(view: View) {
        adapterBrandLap = BrandLapName_Adapter(view.context, listBrandName)
        val gridLayoutManager =
            GridLayoutManager(view.context, 3, LinearLayoutManager.VERTICAL, false)
        reclerView_brandLap.layoutManager = gridLayoutManager
        reclerView_brandLap.setHasFixedSize(true)
        getBrand()
        reclerView_brandLap.adapter = adapterBrandLap
    }

    private fun addImageSliderItems() {
        val sliderImage = SliderImage(R.drawable.laptopmacbook_slider, "Laptop Macbook Pro")
        listImageSlider.add(sliderImage)

        val sliderImage2 = SliderImage(R.drawable.laptopacer_slider, "Laptop Acer")
        listImageSlider.add(sliderImage2)

        val sliderImage3 = SliderImage(R.drawable.laptopasus_slider, "Laptop Asus")
        listImageSlider.add(sliderImage3)

        val sliderImage4 = SliderImage(R.drawable.laptopdell_slider, "Laptop Dell")
        listImageSlider.add(sliderImage4)

        val sliderImage5 = SliderImage(R.drawable.laptophp_slider, "Laptop HP")
        listImageSlider.add(sliderImage5)

        val sliderImage6 = SliderImage(R.drawable.laptoplenovo_slider, "Laptop Lenovo")
        listImageSlider.add(sliderImage6)
        Log.d(listSliderTAG, "Current size is ${listImageSlider.size}")
    }

    private fun checkListCart(){
        if(listLapOrder.size > 0){
            tv_amountCart.visibility = View.VISIBLE
            tv_amountCart.text = listLapOrder.size.toString()
        }else{
            tv_amountCart.visibility = View.GONE
        }
    }

    private fun getListCart(){
        DB_CART.orderByChild("idUser").equalTo(current_userID).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Log.d("DbError_getListCartHome",error.toString())
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                listLapOrder.clear()
                getModelCart(snapshot)
            }
        })
    }

    private fun getModelCart(snapShot: DataSnapshot){
        for (param in snapShot.children){
            val cartModel = param.getValue(Cart::class.java)
            if (cartModel != null){
                listLapOrder = cartModel.listLapOrder
            }
        }
    }

    @SuppressLint("LongLogTag")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        addImageSliderItems()
        Log.d(TAG, "OnAttach is being called !")
    }

    @SuppressLint("LongLogTag")
    override fun onStop() {
        super.onStop()
        adapterImageSlider.deleteAllItem()
        Log.d(TAG, "onStop is being called !")
    }

}