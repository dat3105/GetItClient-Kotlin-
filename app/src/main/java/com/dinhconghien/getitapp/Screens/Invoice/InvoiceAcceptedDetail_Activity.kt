package com.dinhconghien.getitapp.Screens.Invoice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dinhconghien.getitapp.Adapter.ItemInvoiceAccepted_Adapter
import com.dinhconghien.getitapp.Models.ItemInvoiceAccepted
import com.dinhconghien.getitapp.R
import kotlinx.android.synthetic.main.activity_invoice_accepted_detail_.*
import kotlinx.android.synthetic.main.activity_payment.*

class InvoiceAcceptedDetail_Activity : AppCompatActivity() {
    var listItemAccepted = ArrayList<ItemInvoiceAccepted>()
    private lateinit var adapterItemAccepted: ItemInvoiceAccepted_Adapter
    private lateinit var itemInvoiceAccepted: ItemInvoiceAccepted
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice_accepted_detail_)

        setSupportActionBar(toolbar_invoiceAccepted)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getListLapItem()
    }

    private fun addListItem(){
        itemInvoiceAccepted = ItemInvoiceAccepted(R.drawable.hinhnenmac,"Vostro","Laptop DELL",
            12000000,1)
        listItemAccepted.add(itemInvoiceAccepted)

        itemInvoiceAccepted = ItemInvoiceAccepted(R.drawable.hinhnenmac,"Vostro","Laptop DELL",
            12000000,1)
        listItemAccepted.add(itemInvoiceAccepted)
    }

    private fun getListLapItem(){
        adapterItemAccepted = ItemInvoiceAccepted_Adapter(listItemAccepted)
        rcView_invoiceAcceptedScreen.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        rcView_invoiceAcceptedScreen.adapter = adapterItemAccepted
    }

    override fun onStart() {
        super.onStart()
        addListItem()
    }
}