package com.dinhconghien.getitapp.Screens.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dinhconghien.getitapp.MainActivity
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.Screens.GetBackPassword.GetBackPassActivity
import com.dinhconghien.getitapp.Screens.Register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tv_loginHere_loginScreen.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        btn_loginScreen.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        tv_forgotPass_loginScreen.setOnClickListener {
            val intent = Intent(this,GetBackPassActivity::class.java)
            startActivity(intent)
        }

    }
}