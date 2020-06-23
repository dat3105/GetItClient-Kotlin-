package com.dinhconghien.getitapp.Screens.Account

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.dinhconghien.getitapp.MainActivity
import com.dinhconghien.getitapp.R
import kotlinx.android.synthetic.main.activity_edit_account_.*

class EditAccount_Activity : AppCompatActivity() {

    val IMAGE_PICK_CODE =1000
    val PERMISSION_CODE = 1001
    var uri : Uri = Uri.parse("android.resource://com.dinhconghien.getitapp/"+ R.drawable.acer)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_account_)

        imv_back_editAccountScreen.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        btn_editAva_editAccountScreen.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                }
                else{
                    //permission already granted
                    imageFromGallery()
                }
            }
            else{
                imageFromGallery()
            }
        }
    }

    private fun imageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            PERMISSION_CODE -> {
                if(grantResults.size > 0 && grantResults[0]==
                    PackageManager.PERMISSION_DENIED){
                    imageFromGallery()
                }
                else{
                    Toast.makeText(this, "PERMISSION_DENIED", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            uri = data?.data!!
            Glide.with(this).load(uri).placeholder(R.drawable.tran).into(imv_avatar_editAccountScreen)
        }
    }
}