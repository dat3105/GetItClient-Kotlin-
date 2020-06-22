package com.dinhconghien.getitapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dinhconghien.getitapp.Models.ListLapPayment
import com.dinhconghien.getitapp.R

class ListLapPayment_Adapter(val listLapPayment: ArrayList<ListLapPayment>) :
    RecyclerView.Adapter<ListLapPayment_Adapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_listlap_payment, null)
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return listLapPayment.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lapPayment = listLapPayment.get(position)
        holder.tv_nameLap.text = lapPayment.nameLap
        holder.tv_brandLap.text = lapPayment.brandName
        holder.tv_priceLap.text = "${lapPayment.price} VNĐ"
        holder.tv_amountLap.text = "x ${lapPayment.amount}"
        Glide.with(holder.itemView)
            .load(lapPayment.avaLap)
            .fitCenter()
            .into(holder.imv_avaLap)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imv_avaLap: ImageView = itemView.findViewById(R.id.imv_avaLap_paymentItem)
        val tv_nameLap: TextView = itemView.findViewById(R.id.tv_nameLapTop_paymentItem)
        val tv_brandLap: TextView = itemView.findViewById(R.id.tv_nameBrand_paymentItem)
        val tv_priceLap: TextView = itemView.findViewById(R.id.tv_priceLap_paymentItem)
        val tv_amountLap: TextView = itemView.findViewById(R.id.tv_amount_paymentItem)
    }
}