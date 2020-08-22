package com.dinhconghien.getitapp.Screens.Account

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.dinhconghien.getitapp.Models.User
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.Screens.Login.LoginActivity
import com.dinhconghien.getitapp.Utils.SharePreference_Utils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class AccountFragment : Fragment()  {
    lateinit var imvAva : ImageView
    lateinit var tvUserName : TextView
    lateinit var tvEmail : TextView
    lateinit var tvphoneNum : TextView
    lateinit var btnEdit : LinearLayout
    lateinit var btnNoti : LinearLayout
    lateinit var btnShareApp : LinearLayout
    lateinit var btnAboutUs : LinearLayout
    lateinit var btnLogout : LinearLayout
    var dbReference = FirebaseDatabase.getInstance().getReference().child("user")
     var userID =""
    val TAG = "DbError_Account"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        init(view)
        var utils = SharePreference_Utils(view.context)
        userID = utils.getSession()
        fetchDataAndUpdateUI(userID,view)

        btnEdit.setOnClickListener {
            val intent = Intent(view?.context,EditAccount_Activity::class.java)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            showCustomDialog(view)
        }

        btnAboutUs.setOnClickListener {
            val intent = Intent(view?.context,AboutUs_Activity::class.java)
            startActivity(intent)
        }

        btnShareApp.setOnClickListener {
            actionShare()
        }


        return view
    }

    private fun init(view: View){
        imvAva = view.findViewById(R.id.imv_avatar_accountScreen)
        tvUserName = view.findViewById(R.id.tv_username_accountScreen)
        tvEmail = view.findViewById(R.id.tv_email_accountScreen)
        tvphoneNum = view.findViewById(R.id.tv_phone_accountScreen)
        btnEdit = view.findViewById(R.id.linear_editaccountScreen)
        btnShareApp = view.findViewById(R.id.linear_shareApp_accountSceen)
        btnAboutUs = view.findViewById(R.id.linear_aboutUs_accountScreen)
        btnLogout = view.findViewById(R.id.linear_logOut_accountScreen)
    }

    private fun actionShare() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            val url = "https://appurl.io/kFN2UWjUF"
            shareIntent.putExtra(Intent.EXTRA_TEXT, url)
            startActivity(Intent.createChooser(shareIntent, "Chọn 1 ứng dụng"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showCustomDialog(view: View) {
        val viewGroup = view.findViewById<ViewGroup>(android.R.id.content)
        val dialogView: View = LayoutInflater.from(view.context).inflate(R.layout.dialog_logout, viewGroup, false)
        val builder = AlertDialog.Builder(view.context)
        builder.setView(dialogView)
        val alertDialog = builder.create()
        val imv_back =
            dialogView.findViewById<ImageView>(R.id.imv_back_dialogLogout)
        val btnYes = dialogView.findViewById<Button>(R.id.btn_yes_dialogLogout)
        val btnNo = dialogView.findViewById<Button>(R.id.btn_no_dialogLogout)

        btnYes.setOnClickListener {
          dbReference.child(userID).child("wasOnline").setValue(false)
            val sessionManagement = SharePreference_Utils(view.context)
            sessionManagement.removeSession()
            val intent = Intent(view?.context,LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        btnNo.setOnClickListener {
            alertDialog.dismiss()
        }
        imv_back.setOnClickListener { alertDialog.dismiss() }
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()
    }

    fun fetchDataAndUpdateUI(userID: String,view: View)  {
       dbReference.child(userID)
           .addListenerForSingleValueEvent(object : ValueEventListener{
               override fun onCancelled(error: DatabaseError) {
                  Log.d(TAG,"$error")
               }
               override fun onDataChange(snapshot: DataSnapshot) {
                   var user = snapshot.getValue(User::class.java)
                   if (user != null) {
                       try {
                           if (user.avaUser == "") {
                               tvUserName.text = user.userName
                               tvEmail.text = user.email
                               tvphoneNum.text = user.phone
                               imvAva.setImageResource(R.drawable.ic_person)
                           }
                           else{
                               tvUserName.text = user.userName
                               tvEmail.text = user.email
                               tvphoneNum.text = user.phone
                               Glide.with(view.context).load(user.avaUser).fitCenter().into(imvAva)
                           }
                       }catch (e:Exception){
                           e.printStackTrace()
                       }

                   }
               }
           })
    }

}