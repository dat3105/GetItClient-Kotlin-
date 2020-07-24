package com.dinhconghien.getitapp.Screens.Cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dinhconghien.getitapp.Adapter.ListLapPayment_Adapter
import com.dinhconghien.getitapp.Models.ListLapPayment
import com.dinhconghien.getitapp.R
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : AppCompatActivity() {
    var listLapPayment = ArrayList<ListLapPayment>()
    private lateinit var adapterLapPayment: ListLapPayment_Adapter
    private lateinit var lapPayment: ListLapPayment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        setSupportActionBar(toolbar_paymentScreen)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getListLapItem()
    }

    private fun addListItem(){
        lapPayment = ListLapPayment(R.drawable.hinhnenmac,"Vostro","Laptop DELL",
        12000000,1)
        listLapPayment.add(lapPayment)

        lapPayment = ListLapPayment(R.drawable.hinhnenmac,"Vostro","Laptop DELL",
            12000000,1)
        listLapPayment.add(lapPayment)

        lapPayment = ListLapPayment(R.drawable.hinhnenmac,"Vostro","Laptop DELL",
            12000000,1)
        listLapPayment.add(lapPayment)

        lapPayment = ListLapPayment(R.drawable.hinhnenmac,"Vostro","Laptop DELL",
            12000000,1)
        listLapPayment.add(lapPayment)
    }

    private fun getListLapItem(){
        adapterLapPayment = ListLapPayment_Adapter(listLapPayment)
        rcView_listItem_paymentScreen.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rcView_listItem_paymentScreen.adapter = adapterLapPayment
    }

    override fun onStart() {
        super.onStart()
        addListItem()
    }
}