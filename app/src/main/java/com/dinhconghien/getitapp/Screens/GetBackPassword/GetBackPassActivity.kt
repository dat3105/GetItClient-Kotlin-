package com.dinhconghien.getitapp.Screens.GetBackPassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.dinhconghien.getitapp.Models.User
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.Screens.Login.LoginActivity
import com.dinhconghien.getitapp.UI.CustomToast
import com.dinhconghien.getitapp.UI.DialogLoading
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_get_back_pass.*
import kotlinx.coroutines.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class GetBackPassActivity : AppCompatActivity() {
    private val email_pattern = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$"
    lateinit var patternEmail: Pattern
    lateinit var matcherEmail: Matcher
    lateinit var dialogLoading: DialogLoading
    var dbReference = FirebaseDatabase.getInstance().getReference().child("user")
    var listUser = ArrayList<User>()
   var user : User = User()
    var TAG = "Check user forgotPass"
    lateinit var job: Job
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_back_pass)
        job = Job()
        imv_back_getBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_confirm_getBackScreen.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) { getBackPass() }
        }
    }

    override fun onStart() {
        super.onStart()
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
        }
        Log.d(TAG,"Size of listUser of onStart :${listUser.size}")
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    suspend fun getBackPass() {
        val email = et_email_getBackScreen.text.toString()
        val phone = et_phoneNumber_getBackScreen.text.toString()
        checkValidateForm(email, phone)
        val checkValidate = checkValidateForm(email, phone)
        if (checkValidate == true) {
            withContext(Dispatchers.Main) {
                dialogLoading = DialogLoading(this@GetBackPassActivity)
                dialogLoading.show()
//                delay(1500L)
                val password = async(Dispatchers.IO) { checkInfo(email, phone) }.await()
                if (password == "") {
                    CustomToast.makeText(
                        this@GetBackPassActivity,
                        "Thông tin đéo đúng nhé",
                        Toast.LENGTH_LONG,
                        3
                    )?.show()
                    dialogLoading.dismiss()
                }else{
                    CustomToast.makeText(
                        this@GetBackPassActivity,
                        "Mật khẩu của khứa là : $password",
                        Toast.LENGTH_LONG,
                        1
                    )?.show()
                    dialogLoading.dismiss()
                }

            }

        }
    }

    fun checkValidateForm(email: String, phone: String): Boolean {
        patternEmail = Pattern.compile(email_pattern)
        matcherEmail = patternEmail.matcher(email)
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(phone)) {
            CustomToast.makeText(this, "Không được để trống thông tin", Toast.LENGTH_LONG, 2)
                ?.show()
            return false
        } else if (email.contains(" ")) {
            CustomToast.makeText(
                this,
                "Không được để khoảng trắng ở 'Email' ",
                Toast.LENGTH_LONG,
                2
            )?.show()
            return false
        } else if (phone.contains(" ")) {
            CustomToast.makeText(
                this,
                "Không được để khoảng trắng ở 'Số điện thoại' ",
                Toast.LENGTH_LONG,
                2
            )?.show()
            return false
        } else if (phone.length != 10) {
            CustomToast.makeText(this, "Sai định dạng số phone", Toast.LENGTH_LONG, 2)?.show()
            return false
        } else if (!matcherEmail.matches()) {
            CustomToast.makeText(this, "Sai định dạng email", Toast.LENGTH_LONG, 2)?.show()
            return false
        }
        return true
    }

    fun getUser(snapShot: DataSnapshot) {
        for (param in snapShot.children) {
            user = param.getValue(User::class.java)!!
            listUser.add(user)
        }
    }


    fun checkInfo(email: String, phone: String): String {
            var password = ""
            for (i in 0 until listUser.size) {
                if (listUser[i].email.equals(email) && listUser[i].phone.equals(phone)) {
                    password = listUser[i].password
                }
            }
        return password
    }

}