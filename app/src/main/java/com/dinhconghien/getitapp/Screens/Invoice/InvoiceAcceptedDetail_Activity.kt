package com.dinhconghien.getitapp.Screens.Invoice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dinhconghien.getitapp.R
import kotlinx.android.synthetic.main.activity_invoice_accepted_detail_.*

class InvoiceAcceptedDetail_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice_accepted_detail_)

        setSupportActionBar(toolbar_invoiceAccepted)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        getListLapItem()
    }

//    private fun getListLapItem(){
//        adapterItemAccepted = ItemInvoiceAccepted_Adapter(listItemAccepted)
//        rcView_invoiceAcceptedScreen.layoutManager = LinearLayoutManager(this,
//            LinearLayoutManager.VERTICAL,false)
//        rcView_invoiceAcceptedScreen.adapter = adapterItemAccepted
//    }

}