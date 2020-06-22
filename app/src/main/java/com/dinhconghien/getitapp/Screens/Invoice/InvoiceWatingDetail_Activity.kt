package com.dinhconghien.getitapp.Screens.Invoice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dinhconghien.getitapp.R
import kotlinx.android.synthetic.main.activity_invoice_wating_detail_.*
import kotlinx.android.synthetic.main.activity_list_lap_detail.*

class InvoiceWatingDetail_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice_wating_detail_)
        setSupportActionBar(toolbar_invoiceWatingDetail)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }
}