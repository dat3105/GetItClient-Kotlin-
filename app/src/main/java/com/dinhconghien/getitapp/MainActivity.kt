package com.dinhconghien.getitapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dinhconghien.getitapp.Screens.Account.AccountFragment
import com.dinhconghien.getitapp.Screens.Home.HomeFragment
import com.dinhconghien.getitapp.Screens.Invoice.InvoiceFragment
import com.dinhconghien.getitapp.Screens.Message.MessageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    openFragment(HomeFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.menu_invoice -> {
                    openFragment(InvoiceFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.menu_message -> {
                    openFragment(MessageFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.menu_account -> {
                    openFragment(AccountFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            return@OnNavigationItemSelectedListener false
        })

        openFragment(HomeFragment())
    }

    fun openFragment(fragment: Fragment?) {
        val transaction =
            supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment!!)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    }



