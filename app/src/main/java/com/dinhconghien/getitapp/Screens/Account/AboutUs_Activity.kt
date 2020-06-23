package com.dinhconghien.getitapp.Screens.Account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dinhconghien.getitapp.R
import kotlinx.android.synthetic.main.activity_about_us_.*
import kotlinx.android.synthetic.main.activity_list_laptop.*

class AboutUs_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us_)

        setSupportActionBar(toolbar_aboutUsScreen)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}