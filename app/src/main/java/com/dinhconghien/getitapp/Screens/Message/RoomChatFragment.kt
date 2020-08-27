package com.dinhconghien.getitapp.Screens.Message

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dinhconghien.getitapp.Adapter.RoomChat_Adapter
import com.dinhconghien.getitapp.Models.RoomChat
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.Utils.SharePreference_Utils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RoomChatFragment : androidx.fragment.app.Fragment() {
    lateinit var swipeRL : SwipeRefreshLayout
    lateinit var rcView_roomChat : RecyclerView
    val DB_ROOMCHAT = FirebaseDatabase.getInstance().getReference("roomChat")
    lateinit var adapterRoomChat : RoomChat_Adapter
    var listRoomChat = ArrayList<RoomChat>()
    lateinit var idUser : String
    val TAG_GETLISTROOM = "DbError_getListRoomChat_RoomChatFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_room_chat, container, false)
        anhXa(view)
        updateUI(view)
        swipeRL.setOnRefreshListener {
            swipeRL.isRefreshing = false
         updateUI(view)
        }
        return view
    }

    private fun anhXa(view: View){
        swipeRL = view.findViewById(R.id.swipeRL_roomChatFrag)
        rcView_roomChat = view.findViewById(R.id.rcView_roomChatFrag)
        val utils = SharePreference_Utils(view.context)
        idUser = utils.getSession()
    }

     fun updateUI(view: View){
//        val dialogLoading = DialogLoading(view.context)
//        dialogLoading.show()
//        delay(500L)
        setUpRoomAdapter(view)
    }

    private fun setUpRoomAdapter(view: View){
        adapterRoomChat = RoomChat_Adapter(view.context,listRoomChat,idUser)
        rcView_roomChat.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        rcView_roomChat.setHasFixedSize(true)
        rcView_roomChat.adapter = adapterRoomChat
        getListRoomChat()
        adapterRoomChat.setOnItemClickedListener(object : RoomChat_Adapter.OnItemClickedListener{
            override fun onClicked(position: Int) {
                val idRoomChat = listRoomChat[position].idRoomChat
                DB_ROOMCHAT.child(idRoomChat).child("wasSeenUser").setValue(true)
                DB_ROOMCHAT.child(idRoomChat).child("countUnreadMesUser").setValue(0)
            }

        })

    }

    private fun getListRoomChat(){
        DB_ROOMCHAT.orderByChild("idUser").equalTo(idUser).addValueEventListener(object : ValueEventListener{
            @SuppressLint("LongLogTag")
            override fun onCancelled(error: DatabaseError) {
               Log.d(TAG_GETLISTROOM,error.toString())
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                listRoomChat.clear()
                getRoomModel(snapshot)
            }
        })
    }

    private fun getRoomModel(snapshot: DataSnapshot){
        for (param in snapshot.children){
            val roomCHatModel = param.getValue(RoomChat::class.java)
            if (roomCHatModel != null){
                listRoomChat.add(roomCHatModel)
                adapterRoomChat.setListRoomChatNew(listRoomChat)
            }
        }
    }

}