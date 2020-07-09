package com.dinhconghien.getitapp.Screens.Account

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.dinhconghien.getitapp.MainActivity
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.UI.CustomToast
import com.dinhconghien.getitapp.Utils.SharePreference_Utils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.dinhconghien.getitapp.Models.User
import com.dinhconghien.getitapp.UI.DialogLoading
import kotlinx.android.synthetic.main.activity_edit_account_.*
import kotlinx.coroutines.*

class EditAccount_Activity : AppCompatActivity() {

    val IMAGE_PICK_CODE = 1000
    val PERMISSION_CODE = 1001
    var uri: Uri = Uri.parse("android.resource://com.dinhconghien.getitapp/" + R.drawable.acer)
    var USER_STORE: StorageReference = FirebaseStorage.getInstance().getReference("user")
    var dbReference = FirebaseDatabase.getInstance().getReference("user")
    lateinit var current_userID :String
    val TAG = "Check DbError EditAccount"
    var listUser = ArrayList<User>()
    var job = Job()
    var noti = ""
    var avaUser = ""
    lateinit var user : User
    lateinit var email : String
    lateinit var role : String
     var wasOnline : Boolean = true
    var wasFirstBuy= true
    var addresses = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_account_)
        var utils = SharePreference_Utils(this)
        current_userID = utils.getSession()
        GlobalScope.launch(Dispatchers.IO) {
            dbReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Log.d("DbError", error.toString())
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    listUser.clear()
                    getUser(snapshot)
                }

            })
        } //fetch data from firebase

        imv_back_editAccountScreen.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_editAva_editAccountScreen.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    //permission denied
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                } else {
                    //permission already granted
                    imageFromGallery()
                }
            } else {
                imageFromGallery()
            }
        }

        btn_confirm_accountScreen.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                updateUserInfo()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    fun getUser(snapShot: DataSnapshot) {
        for (param in snapShot.children) {
            var user = param.getValue(User::class.java)!!
            listUser.add(user)
        }
    }

    fun checkInfo(userName: String,phone: String,oldPass: String,newPass: String,avaUser : String): String {
        var noti = "Mật khẩu cũ không chính xác"
        for (i in 0 until listUser.size) {
            if (listUser[i].userID.equals(current_userID)) {
                if (listUser[i].password.equals(oldPass)) {
                    noti = "Cập nhật thông tin thành công"
                    email = listUser[i].email
                    role = listUser[i].role
                    wasOnline = listUser[i].wasOnline
                    wasFirstBuy = listUser[i].wasFirstBuy
                    addresses = listUser[i].userAddress
                }
            }
        }
        return noti
    }

    suspend fun updateUserInfo() {
        val userName = et_name_editAccountScreen.text.toString()
        val phone = et_phoneNum_editAccountScreen.text.toString()
        val oldPass = et_oldPass_editAccountScreen.text.toString()
        val newPass = et_newPass_editAccountScreen.text.toString()
        checkValidateForm(userName, phone, oldPass, newPass)
        val checkVali =  checkValidateForm(userName, phone, oldPass, newPass)
        if(checkVali == true){

            val dialogLoading = DialogLoading(this)
            dialogLoading.show()
            delay(1000L)
            withContext(Dispatchers.IO){
                noti = async { checkInfo(userName,phone,oldPass,newPass,avaUser) }.await()
            }
            if (noti == "Mật khẩu cũ không chính xác")
            {
                CustomToast.makeText(this, noti, Toast.LENGTH_LONG, 3)!!.show()
                dialogLoading.dismiss()
            }
            else
            {
                var store : StorageReference = USER_STORE.child(current_userID)
                store.putFile(uri).addOnCompleteListener{
                    store.downloadUrl.addOnSuccessListener {
                            uri -> avaUser = uri.toString()
                            user = User(current_userID,email,userName,phone,newPass,role,wasOnline,wasFirstBuy,avaUser,addresses)
                        dbReference.child(current_userID).setValue(user)
                        CustomToast.makeText(this, noti, Toast.LENGTH_LONG, 1)!!.show()
                        dialogLoading.dismiss()
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }.addOnFailureListener{it ->
                    CustomToast.makeText(this, it.toString(), Toast.LENGTH_LONG, 3)!!.show()
                    dialogLoading.dismiss()
                }
            }
        }
    }

    private fun imageFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    imageFromGallery()
                } else {
                    Toast.makeText(this, "PERMISSION_DENIED", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE && data != null) {
            uri = data.data!!
            Glide.with(this).load(uri).into(imv_avatar_editAccountScreen)
        }
    }

    private fun checkValidateForm(
        userName: String,
        phone: String,
        oldPass: String,
        newPass: String
    ) : Boolean  {
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(oldPass) || TextUtils.isEmpty(newPass))
        {
            CustomToast.makeText(this, "Không được để trống thông tin", Toast.LENGTH_LONG, 2)!!.show()
            return false
        }
        else if (userName.startsWith(" ") || userName.endsWith(" ")) {
            CustomToast.makeText(this, "Không được để khoảng trắng ở 'Họ và tên'", Toast.LENGTH_LONG, 2)!!.show()
            return false
        }
        else if (phone.contains(" "))
        {
            CustomToast.makeText(this, "Không được để khoảng trắng ở 'Số điện thoại'", Toast.LENGTH_LONG, 2)!!.show()
            return false
        }
        else if (oldPass.contains(" "))
        {
            CustomToast.makeText(this, "Không được để khoảng trắng ở 'Mật khẩu cũ'", Toast.LENGTH_LONG, 2)!!.show()
            return false
        }
        else if (newPass.contains(" "))
        {
            CustomToast.makeText(this, "Không được để khoảng trắng ở 'Mật khẩu mới'", Toast.LENGTH_LONG, 2)!!.show()
            return false
        }
        else if (uri == null || uri == Uri.parse("android.resource://com.dinhconghien.getitapp/"+ R.drawable.acer)){
            CustomToast.makeText(this,"Không được đễ trống hình",Toast.LENGTH_LONG,2)!!.show()
            return false
        }
        return true
    }
}