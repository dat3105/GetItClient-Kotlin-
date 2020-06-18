package com.dinhconghien.getitapp.Screens.ListLaptop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dinhconghien.getitapp.Adapter.Cart_Adapter
import com.dinhconghien.getitapp.Adapter.ListLaptop_Adapter
import com.dinhconghien.getitapp.Models.Cart
import com.dinhconghien.getitapp.Models.ListLaptop
import com.dinhconghien.getitapp.R
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_list_laptop.*
import kotlinx.android.synthetic.main.item_brandlaptop.*
import kotlinx.android.synthetic.main.item_listlaptop.*

class ListLaptopActivity : AppCompatActivity() {
    private lateinit var adapterListLap: ListLaptop_Adapter
    private lateinit var listLaptop: ListLaptop
    private var dsLap = ArrayList<ListLaptop>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_laptop)

        setSupportActionBar(toolbar_listLaptopScreen)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val brandLapName  = intent.getStringExtra("brandLapName")
        toolbar_listLaptopScreen.title = brandLapName

        addLapItem()
    }

    private fun addLapItem(){
        listLaptop = ListLaptop(R.drawable.avatarlaptop,"Vostro",
            4,12000000,"Laptop Dell",10)
        dsLap.add(listLaptop)

        listLaptop = ListLaptop(R.drawable.avatarlaptop,"Vostro",
            7,12000000,"Laptop Dell",10)
        dsLap.add(listLaptop)

        listLaptop = ListLaptop(R.drawable.avatarlaptop,"Vostro",
            3,12000000,"Laptop Dell",10)
        dsLap.add(listLaptop)
    }

    private fun getLapItem() {
        adapterListLap = ListLaptop_Adapter(this, dsLap)
        val gridLayoutManager =
            GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
        rcView_listLapScreen.layoutManager = gridLayoutManager
        rcView_listLapScreen.setHasFixedSize(true)
        rcView_listLapScreen.adapter = adapterListLap
    }

    override fun onStart() {
        super.onStart()
        getLapItem()
    }
}