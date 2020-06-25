package com.dinhconghien.getitapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dinhconghien.getitapp.Models.MessageUser
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.Screens.ListLapDetail.ListLapDetailActivity
import com.dinhconghien.getitapp.Screens.Message.MessageContent_Activity

class UserMessage_Adapter(val listMes : ArrayList<MessageUser>) : RecyclerView.Adapter<UserMessage_Adapter.ViewHolder>() {
   lateinit var myContext : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserMessage_Adapter.ViewHolder {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list_message, null)
        myContext = parent.context
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
      return listMes.size
    }

    override fun onBindViewHolder(holder: UserMessage_Adapter.ViewHolder, position: Int) {
       val message = listMes.get(position)
        var unReadMes = message.unReadMesage
        holder.tv_userName.text = message.userName
        holder.tv_lastMes.text = message.lastMessage
        holder.tv_date.text = message.date

        if (unReadMes == 0){
            holder.tv_unReadMes.visibility = View.GONE
        }else{
            holder.tv_unReadMes.text = "${message.unReadMesage}"
        }

        if (!message.isOnline){
            holder.imv_isOnline.setBackgroundResource(R.drawable.bg_offline)
        }

        if (message.avaUser == null){
            holder.imv_avaUser.setImageResource(R.drawable.ic_person)
        }else{
            Glide.with(holder!!.itemView)
                .load(message.avaUser)
                .fitCenter()
                .into(holder.imv_avaUser)
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(myContext, MessageContent_Activity::class.java)
            intent.putExtra("userName",message.userName)
            intent.putExtra("avaUser",message.avaUser)
            myContext.startActivity(intent)
        }

    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var imv_avaUser : ImageView = itemView.findViewById(R.id.imv_avatar_itemListMessage)
        val tv_unReadMes : TextView = itemView.findViewById(R.id.tv_unReadMes_listMesItem)
        val imv_isOnline : ImageView = itemView.findViewById(R.id.imv_online_listMesItem)
        val tv_userName : TextView = itemView.findViewById(R.id.tv_userName_listMessageItem)
        val tv_lastMes : TextView = itemView.findViewById(R.id.tv_lastMessage_listMessageItem)
        val tv_date : TextView = itemView.findViewById(R.id.tv_date_listMessageItem)
    }
}