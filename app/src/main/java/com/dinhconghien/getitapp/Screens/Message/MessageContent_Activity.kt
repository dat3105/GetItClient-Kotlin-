package com.dinhconghien.getitapp.Screens.Message

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dinhconghien.getitapp.Adapter.MessageContent_Adapter
import com.dinhconghien.getitapp.MainActivity
import com.dinhconghien.getitapp.Models.MessageContent
import com.dinhconghien.getitapp.R
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_message_content_.*

class MessageContent_Activity : AppCompatActivity() {
    lateinit var adapterMes : MessageContent_Adapter
    lateinit var messageContent: MessageContent
    val listMesContent = ArrayList<MessageContent>()
    lateinit var userName : String
    var avaUser  : Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_content_)
//        var tb_mesContent : androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_mesContent)
//        setSupportActionBar(tb_mesContent)
//        supportActionBar?.setDisplayShowHomeEnabled(true)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        userName = intent.getStringExtra("userName")
        avaUser = intent.getIntExtra("avaUser",R.drawable.ic_person)
        var tv_userName = findViewById<TextView>(R.id.tv_nameUser_mesContent)
        val imv_avaUser = findViewById<CircleImageView>(R.id.imv_avaUser_mesContent)

        tv_userName.text = userName

        Glide.with(this)
            .load(avaUser)
            .fitCenter()
            .into(imv_avaUser)

        imv_back_mesContent.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        getMesItem()

    }

    private fun addMesItem(){
        avaUser = intent.getIntExtra("avaUser",R.drawable.ic_person)
        messageContent = MessageContent("Hello",true,date = "14:30 25/06/2020",avaGuest = avaUser)
        listMesContent.add(messageContent)

        messageContent = MessageContent("Hello a Hiến cu bự hihi <3",false
            ,date = "14:30 25/06/2020",avaGuest = avaUser!!)
        listMesContent.add(messageContent)

        messageContent = MessageContent("Cho a bú dú cái nha <3",true
            ,date = "14:30 25/06/2020",avaGuest = avaUser!!)
        listMesContent.add(messageContent)

        messageContent = MessageContent("Bú cái lz má mày !!!",false
            ,date = "14:30 25/06/2020",avaGuest = avaUser!!)
        listMesContent.add(messageContent)

        messageContent = MessageContent("Sao dị??? A hứa a bú chíu hoy",true
            ,date = "14:30 25/06/2020",avaGuest = avaUser!!)
        listMesContent.add(messageContent)

        messageContent = MessageContent("Kêu má mày cho bú",false
            ,date = "14:30 25/06/2020",avaGuest = avaUser!!)
        listMesContent.add(messageContent)

        messageContent = MessageContent("Đĩ Đạt ngu mà lì!!!",true
            ,date = "14:30 25/06/2020",avaGuest = avaUser!!)
        listMesContent.add(messageContent)

        messageContent = MessageContent("Hihi I agree with you ",false
            ,date = "14:30 25/06/2020",avaGuest = avaUser!!)
        listMesContent.add(messageContent)
    }

    private fun getMesItem(){
        adapterMes = MessageContent_Adapter(listMesContent)
        rcView_mesContent.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rcView_mesContent.adapter = adapterMes
    }

    override fun onStart() {
        super.onStart()
        addMesItem()
    }
}