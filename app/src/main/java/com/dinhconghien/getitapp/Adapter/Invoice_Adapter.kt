package com.dinhconghien.getitapp.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dinhconghien.getitapp.Models.Invoice
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.Screens.Invoice.InvoiceWatingDetail_Activity
import com.dinhconghien.getitapp.Screens.Login.LoginActivity

class Invoice_Adapter(val context: Context, val listInvoice : ArrayList<Invoice>) :RecyclerView.Adapter<Invoice_Adapter.ViewHolder>() {
    lateinit var myContext: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.item_invoice_waiting, null)
        myContext = parent.context
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return listInvoice.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val invoiceWating = listInvoice.get(position)
        holder.tv_idInvoice.text = invoiceWating.idInvoice
        holder.tv_timeOrder.text = invoiceWating.timeOrder
        holder.tv_sumPrice.text  = "${invoiceWating.sumPrice} VNĐ"
        holder.tv_status.text    = invoiceWating.status
        if (invoiceWating.status.contains("Đang chờ xác nhận",true)){
            holder.tv_status.setTextColor(Color.parseColor("#00A65C"))
        }
        if (invoiceWating.status.contains("Đã giao",true)){
            holder.tv_status.setTextColor(Color.parseColor("#2F85FF"))
        }

        Glide.with(holder!!.itemView)
            .load(R.drawable.bill)
            .fitCenter()
            .into(holder.imv_bill)
        holder.itemView.setOnClickListener {
            val status = invoiceWating.status
            if (status.contains("Đang chờ xác nhận",true)){
                val intent = Intent(myContext,InvoiceWatingDetail_Activity::class.java)
                myContext.startActivity(intent)
            }
            if (status.contains("Đã giao",true)){
                val intent = Intent(myContext,LoginActivity::class.java)
                myContext.startActivity(intent)
            }

        }
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val imv_bill : ImageView = itemView.findViewById(R.id.imv_bill_inVoiceWating)
        val tv_idInvoice : TextView = itemView.findViewById(R.id.tv_idInvoiceWating_Item)
        val tv_timeOrder : TextView = itemView.findViewById(R.id.tv_timeOrder_itemInvoiceWating)
        val tv_sumPrice  : TextView = itemView.findViewById(R.id.tv_sumPrice_itemInvoiceWating)
        val tv_status    : TextView = itemView.findViewById(R.id.tv_status_itemInvoiceWating)

    }
}