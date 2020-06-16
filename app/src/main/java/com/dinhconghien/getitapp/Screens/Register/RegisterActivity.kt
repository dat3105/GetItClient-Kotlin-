package com.dinhconghien.getitapp.Screens.Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dinhconghien.getitapp.MainActivity
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.Screens.Login.LoginActivity
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tv_loginHere_registerScreen.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}