package com.dinhconghien.getitapp.Screens.Message

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dinhconghien.getitapp.Adapter.ChatMessage_Adapter
import com.dinhconghien.getitapp.MainActivity
import com.dinhconghien.getitapp.Models.ChatMessage
import com.dinhconghien.getitapp.Models.RoomChat
import com.dinhconghien.getitapp.Models.User
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.UI.DialogLoading
import com.dinhconghien.getitapp.Utils.SharePreference_Utils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_message_content_.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MessageContent_Activity : AppCompatActivity() {
    lateinit var adapterMesChat: ChatMessage_Adapter
    val listMesContent = ArrayList<ChatMessage>()
    var userName = ""
    var avaUser = ""
    var avaAdmin = ""
    var idUser = ""
    var idAdmin = ""
    var idRoomChat = ""
    val DB_CHATMESSAGE = FirebaseDatabase.getInstance().getReference("chatMessage")
    val DB_ADMIN = FirebaseDatabase.getInstance().getReference("user")
    val DB_ROOMCHAT = FirebaseDatabase.getInstance().getReference("roomChat")
    var listChatMes = ArrayList<ChatMessage>()
    val TAG_GETLISTCHATMES = "DbError_getListChat_MesContent"
    val TAG_GETADMIN = "DbError_getAdmin_mesContent"
    val TAG_GETROOM = "DbError_getRoom_mesContent"
    var adminName = ""
    var listRoomChat = ArrayList<RoomChat>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_content_)
        init()
        updateUI()
        swipeRL_mesContent.setOnRefreshListener {
            swipeRL_mesContent.isRefreshing = false
            updateUI()
        }

        imv_send_mesContent.setOnClickListener {
            val messageUser = et_chatBox_mesContent.text.toString()
            sendMes(messageUser)
            et_chatBox_mesContent.setText("")
        }

        imv_back_mesContent.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            DB_ROOMCHAT.child(idRoomChat).child("wasSeenUser").setValue(false)
            DB_ROOMCHAT.child(idRoomChat).child("avaUser").setValue(avaUser)
            DB_ROOMCHAT.child(idRoomChat).child("userName").setValue(userName)
            startActivity(intent)
            finish()
        }

    }

    private fun init() {
        val utils = SharePreference_Utils(this)
        idUser = utils.getSession()
        idAdmin = intent.getStringExtra("idAdmin")
        idRoomChat = intent.getStringExtra("idRoomChat")
        userName = intent.getStringExtra("userName")
        avaUser = intent.getStringExtra("avaUser")

    }

    fun updateUI() {
        val dialogLoading = DialogLoading(this)
        dialogLoading.show()
        getAdmin()
        getMesItem()
        dialogLoading.dismiss()
    }

    private fun sendMes(messageUser: String) {
        DB_ROOMCHAT.orderByChild("idRoomChat").equalTo(idRoomChat)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                @SuppressLint("LongLogTag")
                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG_GETROOM, error.toString())
                }

                @RequiresApi(Build.VERSION_CODES.O)
                override fun onDataChange(snapshot: DataSnapshot) {
                    listRoomChat.clear()
                    getRoomModel(snapshot, messageUser)
                }
            })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getRoomModel(snapshot: DataSnapshot, messageUser: String) {
        for (param in snapshot.children) {
            val roomModel = param.getValue(RoomChat::class.java)!!
            val avaAdmin = roomModel.avaAdmin
            val adminName = roomModel.adminName
            val wasSeenAdmin = roomModel.wasSeenAdmin
            val countUnreadAdmin = roomModel.countUnreadMesAdmin
            if (wasSeenAdmin == true) {
                setUpMes(0, messageUser, true, avaAdmin, adminName, true)
            } else {
                setUpMes(countUnreadAdmin + 1, messageUser, false, avaAdmin, adminName, false)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUpMes(
        countUnreadAdmin: Int, messageUser: String, wasSeenAdmin: Boolean,
        avaAdmin: String, adminName: String, wasReadLastMes: Boolean
    ) {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        var idChatMessage = DB_CHATMESSAGE.push().key.toString()
        var date = current.format(formatter)
        val chatMesModel =
            ChatMessage(idChatMessage, idRoomChat, messageUser, date, idUser, idAdmin)
        val roomModel = RoomChat(
            idRoomChat, idUser, idAdmin, wasSeenAdmin,
            true, avaUser, avaAdmin, countUnreadAdmin, 0
            , userName, adminName, messageUser, date, idUser, wasReadLastMes
        )
        DB_CHATMESSAGE.child(idChatMessage).setValue(chatMesModel)
        DB_ROOMCHAT.child(idRoomChat).setValue(roomModel)
        getMesItem()
    }

    private fun getAdmin() {
        DB_ADMIN.orderByChild("userID").equalTo(idAdmin)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                @SuppressLint("LongLogTag")
                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG_GETADMIN, error.toString())
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    getAdminModel(snapshot)
                }
            })
    }

    private fun  getAdminModel(snapshot: DataSnapshot) {
        for (param in snapshot.children) {
            val adminModel = param.getValue(User::class.java)
            if (adminModel != null) {
                adminName = adminModel.userName!!
                avaAdmin = adminModel.avaUser!!
                DB_ROOMCHAT.child(idRoomChat).child("adminName").setValue(adminName)
                DB_ROOMCHAT.child(idRoomChat).child("avaAdmin").setValue(avaAdmin)
                tv_nameUser_mesContent.text = "Admin $adminName"
                if (avaAdmin != "") {
                    Glide.with(this@MessageContent_Activity).load(avaAdmin).fitCenter()
                        .into(imv_avaUser_mesContent)
                    adapterMesChat.setAvaAdminNew(avaAdmin)
                }
            }
        }
    }

    private fun getMesItem() {
        adapterMesChat = ChatMessage_Adapter(listMesContent, idUser, avaAdmin)
        rcView_mesContent.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        rcView_mesContent.scrollToPosition(listMesContent.size)
        rcView_mesContent.adapter = adapterMesChat
        getListChatMes()
    }

    private fun getListChatMes() {
        DB_CHATMESSAGE.orderByChild("idRoomChat").equalTo(idRoomChat)
            .addValueEventListener(object : ValueEventListener {
                @SuppressLint("LongLogTag")
                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG_GETLISTCHATMES, error.toString())
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    listChatMes.clear()
                    getChatMesModel(snapshot)
                }

            })
    }

    private fun getChatMesModel(snapshot: DataSnapshot) {
        for (param in snapshot.children) {
            val chatMesModel = param.getValue(ChatMessage::class.java)
            if (chatMesModel != null) {
                listChatMes.add(chatMesModel)
                adapterMesChat.setListChatMessage(listChatMes)
                rcView_mesContent.scrollToPosition(listChatMes.count() -1)
            }
        }
    }
}