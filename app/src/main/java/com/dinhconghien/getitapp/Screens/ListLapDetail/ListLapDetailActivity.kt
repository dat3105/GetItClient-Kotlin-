package com.dinhconghien.getitapp.Screens.ListLapDetail

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinhconghien.getitapp.Adapter.ListLaptop_Adapter
import com.dinhconghien.getitapp.Adapter.SliderImage_Adapter
import com.dinhconghien.getitapp.Models.ListLaptop
import com.dinhconghien.getitapp.Models.SliderImage
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.Screens.ListLaptop.ListLaptopActivity
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.activity_list_lap_detail.*
import kotlinx.android.synthetic.main.activity_list_laptop.*

class ListLapDetailActivity : AppCompatActivity() ,View.OnClickListener{
    lateinit var sliderView: SliderView
    lateinit var adapterImageSlider: SliderImage_Adapter
    var listImageSlider = ArrayList<SliderImage>()

    private lateinit var adapterListLap: ListLaptop_Adapter
    private lateinit var listLaptop: ListLaptop
    private var dsLap = ArrayList<ListLaptop>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_lap_detail)

        setSupportActionBar(toolbar_listLapDetailScreen)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sliderView = findViewById(R.id.imageSlider_listLapDetail)
        adapterImageSlider = SliderImage_Adapter(this, listImageSlider)
        addImageSliderItems()

        addLapItem()
    }

     fun showCustomDialog(view: View) {
        val viewGroup = findViewById<ViewGroup>(android.R.id.content)
        //then we will inflate the custom alert dialog xml that we created
        val dialogView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_infolaptop_detail, viewGroup, false)


        //Now we need an AlertDialog.Builder object
        val builder =
            AlertDialog.Builder(this)

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView)

        //finally creating the alert dialog and displaying it
        val alertDialog = builder.create()
        val imv_back =
            dialogView.findViewById<ImageView>(R.id.imv_back_dialogInfoLapDetail)

        imv_back.setOnClickListener { alertDialog.dismiss() }
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()
    }



    private fun addImageSliderItems() {
        val sliderImage = SliderImage(R.drawable.hinhnena, "")
        listImageSlider.add(sliderImage)

        val sliderImage2 = SliderImage(R.drawable.hinhnenmac, "")
        listImageSlider.add(sliderImage2)

        val sliderImage3 = SliderImage(R.drawable.laptopmacbook_slider, "")
        listImageSlider.add(sliderImage3)

    }

    override fun onResume() {
        super.onResume()
        initForSliderImage()
        getLapItem()
    }

    private fun initForSliderImage() {
        sliderView.setSliderAdapter(adapterImageSlider)
        sliderView.setIndicatorAnimation(IndicatorAnimationType.SWAP)
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        sliderView.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT
        sliderView.indicatorUnselectedColor = Color.GRAY
    }

    private fun addLapItem(){
        listLaptop = ListLaptop(R.drawable.avatarlaptop,"Vostro",
            4,12000000,"Laptop Dell",10)
        dsLap.add(listLaptop)

        listLaptop = ListLaptop(R.drawable.avatarlaptop,"Pavillon",
            5,12000000,"Laptop Dell",10)
        dsLap.add(listLaptop)

        listLaptop = ListLaptop(R.drawable.avatarlaptop,"RTX",
            3,12000000,"Laptop Dell",10)
        dsLap.add(listLaptop)

        listLaptop = ListLaptop(R.drawable.avatarlaptop,"LCD",
            3,12000000,"Laptop Dell",10)
        dsLap.add(listLaptop)

        listLaptop = ListLaptop(R.drawable.avatarlaptop,"MVVM",
            3,12000000,"Laptop Dell",10)
        dsLap.add(listLaptop)

        listLaptop = ListLaptop(R.drawable.avatarlaptop,"Vostro",
            3,12000000,"Laptop Dell",10)
        dsLap.add(listLaptop)
    }

    private fun getLapItem() {
        adapterListLap = ListLaptop_Adapter(this, dsLap)
        val gridLayoutManager =
            GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
        rcView_sameType_listLapDetailScreen.layoutManager = gridLayoutManager
        rcView_sameType_listLapDetailScreen.setHasFixedSize(true)
        rcView_sameType_listLapDetailScreen.adapter = adapterListLap
    }


    override fun onClick(v: View?) {
      val intent = Intent(this,ListLaptopActivity::class.java)
        startActivity(intent)
    }


}