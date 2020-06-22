package com.dinhconghien.getitapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dinhconghien.getitapp.Models.ItemInvoiceAccepted
import com.dinhconghien.getitapp.R

class ItemInvoiceAccepted_Adapter(val listItemInvoiceAccepted : ArrayList<ItemInvoiceAccepted>) :
    RecyclerView.Adapter<ItemInvoiceAccepted_Adapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemInvoiceAccepted_Adapter.ViewHolder {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.item_invoice_accepted, null)
        return ViewHolder(inflate)
    }


    override fun getItemCount(): Int {
      return listItemInvoiceAccepted.size
    }

    override fun onBindViewHolder(holder: ItemInvoiceAccepted_Adapter.ViewHolder, position: Int) {
        val item = listItemInvoiceAccepted.get(position)
        holder.tv_nameLap.text = item.nameLap
        holder.tv_brandLap.text = item.brandName
        holder.tv_priceLap.text = "${item.price} VNĐ"
        holder.tv_amountLap.text = "x ${item.amount}"
        Glide.with(holder.itemView)
            .load(item.avaLap)
            .fitCenter()
            .into(holder.imv_avaLap)
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)  {
        val imv_avaLap : ImageView = itemView.findViewById(R.id.imv_avaLap_item_invoiceAccepted)
        val tv_nameLap : TextView = itemView.findViewById(R.id.tv_nameLapTop_invoiceAccepted_Item)
        val tv_brandLap : TextView = itemView.findViewById(R.id.tv_nameBrand_invoiceAccepted_Item)
        val tv_priceLap : TextView = itemView.findViewById(R.id.tv_priceLap_invoiceAccepted_Item)
        val tv_amountLap : TextView = itemView.findViewById(R.id.tv_amount_invoiceAccepted_Item)
    }
}