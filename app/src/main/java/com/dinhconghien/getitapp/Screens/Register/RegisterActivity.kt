package com.dinhconghien.getitapp.Screens.Register

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dinhconghien.getitapp.Models.User
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.Screens.Login.LoginActivity
import com.dinhconghien.getitapp.UI.CustomToast
import com.dinhconghien.getitapp.UI.DialogLoading
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class RegisterActivity : AppCompatActivity() {
    private val email_pattern = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$"
    lateinit var patternEmail: Pattern
    lateinit var matcherEmail: Matcher
    var dbReference = FirebaseDatabase.getInstance().getReference("user")
   var user: User =User()
    var dialogLoading: DialogLoading? = null
    lateinit var job: Job
    var avaUser = "no information"
    var userAddress = "no information"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        job = Job()
        tv_loginHere_registerScreen.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_register_registerScreen.setOnClickListener {
            registerUser()
        }
    }

    fun registerUser() {
        val userName = et_userName_registerScreen.text.toString()
        val email = et_email_registerScreen.text.toString()
        val phone = et_phoneNumber_registerScreen.text.toString()
        val password = et_password_registerScreen.text.toString()
        val confirmPass = et_retypePassword_registerScreen.text.toString()
        checkValidateFormRegister(userName, email, phone, password, confirmPass)
    }

    fun checkValidateFormRegister(
        userName: String, email: String, phone: String,
        password: String, confirmPass: String
    ) {
        patternEmail = Pattern.compile(email_pattern)
        matcherEmail = patternEmail.matcher(email)

        if (userName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPass.isEmpty() || phone.isEmpty()) {
            CustomToast.makeText(this, "Bạn điền thiếu thông tin", Toast.LENGTH_LONG, 2)!!.show()
            return
        } else if (phone.length != 10) {
            CustomToast.makeText(this, "Sai định dạng số phone", Toast.LENGTH_LONG, 2)?.show()
            return
        } else if (!confirmPass.equals(password)) {
            CustomToast.makeText(this, "Mật khẩu chưa khớp", Toast.LENGTH_LONG, 2)!!.show()
            return
        } else if (!matcherEmail.matches()) {
            CustomToast.makeText(this, "Sai định dạng email", Toast.LENGTH_LONG, 2)!!.show()
            return
        } else if (userName.startsWith(" ") || userName.endsWith(" ")) {
            CustomToast.makeText(
                this, "Không được để khoảng trắng đầu và cuối ở 'Tên của bạn'",
                Toast.LENGTH_LONG, 2
            )?.show()
            return
        } else if (email.contains(" ")) {
            CustomToast.makeText(
                this, "Không được để khoảng trắng ở 'Email'",
                Toast.LENGTH_LONG, 2
            )?.show()
            return
        } else if (phone.contains(" ")) {
            CustomToast.makeText(
                this, "Không được để khoảng trắng ở 'Số điện thoại'",
                Toast.LENGTH_LONG, 2
            )!!.show()
            return
        } else if (password.contains(" ")) {
            CustomToast.makeText(
                this, "Không được để khoảng trắng ở 'Mật khẩu'",
                Toast.LENGTH_LONG, 2
            )?.show()
            return
        } else {
            checkDuplcateEmail(email, phone, userName, password)
        }
    }


    fun checkDuplcateEmail(
        email: String,
        phone: String,
        userName: String,
        password: String
    ) {
        val userId: String = dbReference.push().getKey().toString()
        dialogLoading = DialogLoading(this@RegisterActivity)
        dialogLoading!!.show()
        dbReference.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    CustomToast.makeText(
                        this@RegisterActivity,
                        error.toString(),
                        Toast.LENGTH_LONG,
                        3
                    )?.show()
                    dialogLoading!!.dismiss()
                    return
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        CustomToast.makeText(
                            this@RegisterActivity,
                            "Email đã tồn tại",
                            Toast.LENGTH_LONG,
                            3
                        )?.show()
                        dialogLoading!!.dismiss()
                        return
                    } else {
                        try {
                            user = User(userId,email,userName,phone,password,"Customer",false,true)
                            dbReference.child(userId).setValue(user)
                            CustomToast.makeText(
                                this@RegisterActivity,
                                "Đăng kí thành công",
                                Toast.LENGTH_LONG,
                                1
                            )?.show()
                            dialogLoading!!.dismiss()
                            return
                        } catch (e: Exception) {
                            Toast.makeText(
                                this@RegisterActivity,
                                e.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                            Log.d("DbError", "$e")
                            dialogLoading!!.dismiss()
                        }
                    }
                }
            })
    }
}