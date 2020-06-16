package com.dinhconghien.getitapp.Screens.GetBackPassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.Screens.Login.LoginActivity
import com.dinhconghien.getitapp.Screens.Register.RegisterActivity
import kotlinx.android.synthetic.main.activity_get_back_pass.*

class GetBackPassActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_back_pass)

        imv_back_getBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}