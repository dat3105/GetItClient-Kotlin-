package com.dinhconghien.getitapp.Adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dinhconghien.getitapp.Models.ListLaptop
import com.dinhconghien.getitapp.R

class ListLaptop_Adapter(val context: Context , val listLap : ArrayList<ListLaptop>) : RecyclerView.Adapter<ListLaptop_Adapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.item_listlaptop, null)
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
       return listLap.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listLapItem = listLap.get(position)
        holder.tv_nameLap.text = listLapItem.nameLap
        holder.tv_brandLap.text = listLapItem.brandLapName
        holder.tv_priceLap.text = "${listLapItem.priceLap} VNĐ"
        holder.tv_amountLap.text = "Số lượng : ${listLapItem.amountLap} chiếc"
        Glide.with(holder.itemView)
            .load(listLapItem.avatarLap)
            .fitCenter()
            .into(holder.imv_avaLap)
        holder.ratingLap.numStars = listLapItem.rating
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imv_avaLap : ImageView = itemView.findViewById(R.id.imv_avatarLap_listLapItem)
        val tv_nameLap : TextView = itemView.findViewById(R.id.tv_nameLaptop_listLapItem)
        val tv_brandLap : TextView = itemView.findViewById(R.id.tv_brandName_listLapItem)
        val tv_priceLap : TextView = itemView.findViewById(R.id.tv_priceLap_listLapItem)
        val tv_amountLap : TextView = itemView.findViewById(R.id.tv_amountLap_listLapItem)
        val ratingLap : RatingBar = itemView.findViewById(R.id.ratingBar_listLapItem)
    }
}