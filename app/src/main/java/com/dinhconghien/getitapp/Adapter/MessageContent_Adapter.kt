package com.dinhconghien.getitapp.Adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dinhconghien.getitapp.Models.MessageContent
import com.dinhconghien.getitapp.R
import de.hdodenhof.circleimageview.CircleImageView


class MessageContent_Adapter(val listItem: ArrayList<MessageContent>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var VIEW_HOLDER_ME: Int = 0
    var VIEW_HOLDER_YOU: Int = 1

    override fun getItemCount(): Int {

        return if (null != listItem) listItem!!.size else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (listItem!!.get(position).isMe == true) {
            VIEW_HOLDER_ME
        } else {
            VIEW_HOLDER_YOU
        }
    }


    override fun onBindViewHolder(v: RecyclerView.ViewHolder, pos: Int) {
         val mesContent = listItem.get(pos)
        if (v is ViewHolderMe) { // Handle Image Layout
            val viewHolderMe = v

            viewHolderMe.tv_date_host!!.text = mesContent.date
           viewHolderMe.tv_host_mess!!.text = mesContent.mesContent
        } else if (v is ViewHolderYou) { // Handle Video Layout
            val viewHolderYou = v
            viewHolderYou.tv_date_guest!!.text = mesContent.date
            viewHolderYou.tv_guest_mes!!.text = mesContent.mesContent
            if(mesContent.avaGuest == null){
                viewHolderYou.imv_avaGuest!!.setImageResource(R.drawable.ic_person)
            }else{  viewHolderYou.imv_avaGuest?.let {
                Glide.with(viewHolderYou.itemView)
                    .load(mesContent.avaGuest)
                    .fitCenter()
                    .into(it)
            }}

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder = when (viewType) {

            VIEW_HOLDER_ME -> return ViewHolderMe(
                LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_content_host_message,
                    parent,
                    false
                )
            )

            else  -> return ViewHolderYou(
                LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_content_guest_message,
                    parent,
                    false
                )
            )
        }
        return return viewHolder
    }

    inner class ViewHolderMe(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val tv_host_mess = itemView?.findViewById<TextView>(R.id.tv_host_mes_content)
        val tv_date_host = itemView?.findViewById<TextView>(R.id.tv_host_date_content)
    }

    inner class ViewHolderYou(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        val imv_avaGuest = itemView?.findViewById<CircleImageView>(R.id.imv_avatar_itemGuest_content)
        val tv_date_guest = itemView?.findViewById<TextView>(R.id.tv_date_guest_content)
        val tv_guest_mes = itemView?.findViewById<TextView>(R.id.tv_mes_guest_content)
    }
}