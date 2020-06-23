package com.dinhconghien.getitapp.Screens.Account

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.Screens.Login.LoginActivity


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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view = inflater.inflate(R.layout.fragment_account, container, false)
        init(view)

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

        return view
    }

    private fun init(view: View){
        imvAva = view.findViewById(R.id.imv_avatar_accountScreen)
        tvUserName = view.findViewById(R.id.tv_username_accountScreen)
        tvEmail = view.findViewById(R.id.tv_email_accountScreen)
        tvphoneNum = view.findViewById(R.id.tv_phone_accountScreen)
        btnEdit = view.findViewById(R.id.linear_editaccountScreen)
        btnNoti = view.findViewById(R.id.linear_noti_accountscreen)
        btnShareApp = view.findViewById(R.id.linear_shareApp_accountSceen)
        btnAboutUs = view.findViewById(R.id.linear_aboutUs_accountScreen)
        btnLogout = view.findViewById(R.id.linear_logOut_accountScreen)
    }
    fun showCustomDialog(view: View) {
        val viewGroup = view.findViewById<ViewGroup>(android.R.id.content)

        //then we will inflate the custom alert dialog xml that we created
        val dialogView: View =
            LayoutInflater.from(view.context).inflate(R.layout.dialog_logout, viewGroup, false)


        //Now we need an AlertDialog.Builder object
        val builder =
            AlertDialog.Builder(view.context)

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView)

        //finally creating the alert dialog and displaying it
        val alertDialog = builder.create()
        val imv_back =
            dialogView.findViewById<ImageView>(R.id.imv_back_dialogLogout)
        val btnYes = dialogView.findViewById<Button>(R.id.btn_yes_dialogLogout)
        val btnNo = dialogView.findViewById<Button>(R.id.btn_no_dialogLogout)

        btnYes.setOnClickListener {
            val intent = Intent(view?.context,LoginActivity::class.java)
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
}