package com.dinhconghien.getitapp.Screens.Message

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dinhconghien.getitapp.Adapter.ListAdmin_Adapter
import com.dinhconghien.getitapp.Models.RoomChat
import com.dinhconghien.getitapp.Models.User
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.UI.CustomToast
import com.dinhconghien.getitapp.UI.DialogLoading
import com.dinhconghien.getitapp.Utils.SharePreference_Utils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.*
import java.lang.Exception

class ListAdminFragment : Fragment() {
    lateinit var adapterListAdmin: ListAdmin_Adapter
    lateinit var rcView_Mes: RecyclerView
    lateinit var swipeRL: SwipeRefreshLayout
    var listAdmin = ArrayList<User>()
    var idUser = ""
    val DB_USER = FirebaseDatabase.getInstance().getReference("user")
    val DB_ROOMCHAT = FirebaseDatabase.getInstance().getReference("roomChat")
    val TAG_GETADMIN = "DbErrorGetAdmin_ListAdminFragment"
    val TAG_ADDROOMCHAT = "DbErroeAddRoomChat_ListAdminFragment"
    val TAG_GETAVAUSER = "DbError_GetAvaUser_ListAdminFragment"
    val TAG_CHECKLISTROOM = "DbError_CheckListRoom_ListAdminFragment"
    var avaUser = ""
    var wasOnlineUser = true
    var userName = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_admin, container, false)
        anhXa(view)
        updateUI(view)
        swipeRL.setOnRefreshListener {
            swipeRL.isRefreshing = false
            updateUI(view)
        }
        return view
    }

    private fun anhXa(view: View) {
        rcView_Mes = view.findViewById(R.id.rcView_listAdminScreen)
        swipeRL = view.findViewById(R.id.swipeRL_listAdminFragment)
        val utils = SharePreference_Utils(view.context)
        idUser = utils.getSession()
    }

    fun updateUI(view: View) {
        getMesItem(view)
        getListAdmin()
    }

    private fun checkListRoomChat(
        view: View, idRoomChat: String, idAdmin: String, idUser: String, avaAdmin: String,
        wasOnlineAdmin: Boolean, adminName: String
    ) {
        DB_ROOMCHAT.orderByChild("idRoomChat").equalTo(idRoomChat)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                @SuppressLint("LongLogTag")
                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG_CHECKLISTROOM, error.toString())
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (!snapshot.exists()) {
                        DB_USER.orderByChild("userID").equalTo(idUser)
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                @SuppressLint("LongLogTag")
                                override fun onCancelled(error: DatabaseError) {
                                    Log.d(TAG_GETAVAUSER, error.toString())
                                }

                                override fun onDataChange(snapshot: DataSnapshot) {
                                    for (param in snapshot.children) {
                                        val userModel = param.getValue(User::class.java)!!
                                        avaUser = userModel.avaUser!!
                                        wasOnlineUser = userModel.wasOnline
                                        userName = userModel.userName!!
                                        val roomChatModel = RoomChat(
                                            idRoomChat, idUser, idAdmin, false, false,
                                            avaUser, avaAdmin, userName = userName, adminName = adminName)
                                        DB_ROOMCHAT.child(idRoomChat).setValue(roomChatModel)
                                        CustomToast.makeText(
                                            view.context,
                                            "Tạo room chat thành công",
                                            Toast.LENGTH_LONG,
                                            1
                                        )
                                            ?.show()
                                    }
                                }
                            })
                    } else {
                        CustomToast.makeText(
                            view.context,
                            "Bạn đã tạo phòng chat này rồi",
                            Toast.LENGTH_LONG,
                            3
                        )
                            ?.show()
                    }
                }
            })
    }

    private fun getListAdmin() {
        DB_USER.orderByChild("role").equalTo("Admin").addValueEventListener(object :
            ValueEventListener {
            @SuppressLint("LongLogTag")
            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG_GETADMIN, error.toString())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                listAdmin.clear()
                getAdminModel(snapshot)
            }

        })
    }

    private fun getAdminModel(snapshot: DataSnapshot) {
        for (param in snapshot.children) {
            val adminModel = param.getValue(User::class.java)
            if (adminModel != null) {
                listAdmin.add(adminModel)
                adapterListAdmin.setListAdminNew(listAdmin)
            }
        }
    }

    fun getMesItem(view: View) {
        adapterListAdmin = ListAdmin_Adapter(view.context, listAdmin)
        rcView_Mes.layoutManager = LinearLayoutManager(
            view.context, LinearLayoutManager.VERTICAL, false
        )
        rcView_Mes.setHasFixedSize(true)
        rcView_Mes.adapter = adapterListAdmin
        adapterListAdmin.setOnItemClickedListener(object : ListAdmin_Adapter.OnItemClickedListener {
            @SuppressLint("LongLogTag")
            override fun onClicked(position: Int) {
//                val dialogLoading = DialogLoading(view.context)
//                dialogLoading.show()
                try {

                    var idAdmin = listAdmin[position].userID
                    val idRoomChat = "$idAdmin$idUser"
                    val avaAdmin = listAdmin[position].avaUser
                    val wasOnlineAdmin = listAdmin[position].wasOnline
                    val adminName = listAdmin[position].userName
                    checkListRoomChat(
                        view,
                        idRoomChat,
                        idAdmin!!,
                        idUser,
                        avaAdmin!!,
                        wasOnlineAdmin,
                        adminName!!
                    )
//                    dialogLoading.dismiss()
                } catch (e: Exception) {
                    Log.d(TAG_ADDROOMCHAT, e.toString())
//                    dialogLoading.dismiss()
                }
            }
        })
    }
}