package com.dinhconghien.getitapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dinhconghien.getitapp.Models.InvoiceAccepted
import com.dinhconghien.getitapp.R

class InvoiceAccepted_Adapter(val context: Context , val listInvoiceAccepted : ArrayList<InvoiceAccepted>)
    : RecyclerView.Adapter<InvoiceAccepted_Adapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InvoiceAccepted_Adapter.ViewHolder {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.item_invoice_accepted, null)
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
            return listInvoiceAccepted.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val invoiceAccepted = listInvoiceAccepted.get(position)
        holder.tv_idInvoice.text = invoiceAccepted.idInvoice
        holder.tv_timeOrder.text = invoiceAccepted.timeOrder
        holder.tv_sumPrice.text  = "${invoiceAccepted.sumPrice} VNĐ"
        holder.tv_status.text    = invoiceAccepted.status
        Glide.with(holder!!.itemView)
            .load(R.drawable.bill)
            .fitCenter()
            .into(holder.imv_bill)
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val imv_bill : ImageView = itemView.findViewById(R.id.imv_bill_inVoiceAccepted)
        val tv_idInvoice : TextView = itemView.findViewById(R.id.tv_idInvoiceAccepted_Item)
        val tv_timeOrder : TextView = itemView.findViewById(R.id.tv_timeOrder_itemInvoiceAccepted)
        val tv_sumPrice  : TextView = itemView.findViewById(R.id.tv_sumPrice_itemInvoiceAccepted)
        val tv_status    : TextView = itemView.findViewById(R.id.tv_status_itemInvoiceAccepted)
    }
}