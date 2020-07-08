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
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinhconghien.getitapp.Adapter.BrandLapName_Adapter
import com.dinhconghien.getitapp.Adapter.SliderImage_Adapter
import com.dinhconghien.getitapp.Models.BrandLapName
import com.dinhconghien.getitapp.Models.SliderImage
import com.dinhconghien.getitapp.Models.User
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.Screens.Cart.CartActivity
import com.dinhconghien.getitapp.Screens.Login.LoginActivity
import com.dinhconghien.getitapp.Utils.SharePreference_Utils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView


class HomeFragment : Fragment() {
    lateinit var sliderView: SliderView
    lateinit var adapterImageSlider: SliderImage_Adapter
    var listImageSlider = ArrayList<SliderImage>()
    lateinit var tv_welcome : TextView
    lateinit var adapterBrandLap: BrandLapName_Adapter
    lateinit var reclerView_brandLap: RecyclerView
    lateinit var brandLap: BrandLapName
    var listBrandName = ArrayList<BrandLapName>()
    lateinit var imv_cart: ImageView
    val TAG = "Check HomeFragment 's Lifecycle"
    val listSliderTAG = "Check list Slider"
    var dbReference = FirebaseDatabase.getInstance().getReference().child("user")
    @SuppressLint("LongLogTag")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initComponents(view)
        adapterImageSlider = SliderImage_Adapter(view.context, listImageSlider)
        initForSliderImage()
        Log.d(TAG, "OncreateView is being called !")
        getBrandNameItem(view)

        imv_cart.setOnClickListener {
            val intent = Intent(
                view.context,
                CartActivity::class.java
            )
            startActivity(intent)
        }
        var utils = SharePreference_Utils(view.context)
        var current_userID = utils.getSession()
        dbReference.child(current_userID).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
            override fun onDataChange(snapshot: DataSnapshot) {
               var user = snapshot.getValue(User::class.java)
                if (user != null){
                    tv_welcome.text = "Xin chào,${user.userName}"
                }

            }
        })
        return view
    }

    private fun initComponents(view: View) {
        sliderView = view.findViewById(R.id.imageSlider)
        reclerView_brandLap = view.findViewById(R.id.reclerView_itemBrandName_homeScreen)
        imv_cart = view.findViewById(R.id.imv_cart_homeScreen)
        tv_welcome = view.findViewById(R.id.tv_welcome_home)
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

    private fun getBrandNameItem(view: View) {
        adapterBrandLap = BrandLapName_Adapter(view.context, listBrandName)
        val gridLayoutManager =
            GridLayoutManager(view.context, 3, LinearLayoutManager.VERTICAL, false)
        reclerView_brandLap.layoutManager = gridLayoutManager
        reclerView_brandLap.setHasFixedSize(true)
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

    // add brandLap items for inspection
    private fun addBrandLapItem() {
        brandLap = BrandLapName(R.drawable.hp, "Laptop HP")
        listBrandName.add(brandLap)

        brandLap = BrandLapName(R.drawable.dell, "Laptop Dell")
        listBrandName.add(brandLap)

        brandLap = BrandLapName(R.drawable.acer, "Laptop Acer")
        listBrandName.add(brandLap)

        brandLap = BrandLapName(R.drawable.lenovo, "Laptop Lenovo")
        listBrandName.add(brandLap)

        brandLap = BrandLapName(R.drawable.mac, "Laptop Mac")
        listBrandName.add(brandLap)

        brandLap = BrandLapName(R.drawable.asus, "Laptop Asus")
        listBrandName.add(brandLap)

    }

    @SuppressLint("LongLogTag")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        addImageSliderItems()
        addBrandLapItem()
        Log.d(TAG, "OnAttach is being called !")
    }

    @SuppressLint("LongLogTag")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "onActivityCreated is being called !")
    }

    @SuppressLint("LongLogTag")
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart is being called !")
    }

    @SuppressLint("LongLogTag")
    override fun onStop() {
        super.onStop()
        adapterImageSlider.deleteAllItem()
        Log.d(TAG, "onStop is being called !")
    }

}