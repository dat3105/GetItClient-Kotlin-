package com.dinhconghien.getitapp.Screens.Splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.Screens.Login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }, 3000)
    }
}