package com.dinhconghien.getitapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dinhconghien.getitapp.Models.InvoiceWating
import com.dinhconghien.getitapp.R

class InvoiceWating_Adapter(val context: Context , val listInvoiceWating : ArrayList<InvoiceWating>) :RecyclerView.Adapter<InvoiceWating_Adapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InvoiceWating_Adapter.ViewHolder {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.item_invoice_waiting, null)
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return listInvoiceWating.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val invoiceWating = listInvoiceWating.get(position)
        holder.tv_idInvoice.text = invoiceWating.idInvoice
        holder.tv_timeOrder.text = invoiceWating.timeOrder
        holder.tv_sumPrice.text  = "${invoiceWating.sumPrice} VNĐ"
        holder.tv_status.text    = invoiceWating.status
        Glide.with(holder!!.itemView)
            .load(R.drawable.bill)
            .fitCenter()
            .into(holder.imv_bill)
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val imv_bill : ImageView = itemView.findViewById(R.id.imv_bill_inVoiceWating)
        val tv_idInvoice : TextView = itemView.findViewById(R.id.tv_idInvoiceWating_Item)
        val tv_timeOrder : TextView = itemView.findViewById(R.id.tv_timeOrder_itemInvoiceWating)
        val tv_sumPrice  : TextView = itemView.findViewById(R.id.tv_sumPrice_itemInvoiceWating)
        val tv_status    : TextView = itemView.findViewById(R.id.tv_status_itemInvoiceWating)

    }
}