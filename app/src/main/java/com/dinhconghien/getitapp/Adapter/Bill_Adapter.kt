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
import com.dinhconghien.getitapp.Models.Bill
import com.dinhconghien.getitapp.Models.Invoice
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.Screens.Invoice.InvoiceAcceptedDetail_Activity
import com.dinhconghien.getitapp.Screens.Invoice.InvoiceWatingDetail_Activity

class Bill_Adapter(var context: Context, var listInvoice : ArrayList<Bill>) :RecyclerView.Adapter<Bill_Adapter.ViewHolder>() {

    fun setListBill(listInvoice: ArrayList<Bill>){
        this.listInvoice = listInvoice
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.item_invoice_waiting, null)
        context = parent.context
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return listInvoice.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val invoiceWating = listInvoice.get(position)
        holder.tv_idInvoice.text = invoiceWating.idBill
        holder.tv_timeOrder.text = invoiceWating.date
        var price = invoiceWating.sumPrice
        if (price.length == 7) {
            val firstChar = price.substring(0, 1)
            val middleChar = price.substring(1, 4)
            val lastChar = price.substring(4, 7)
            price = "$firstChar.$middleChar.$lastChar"
        } else if (price.length == 8) {
            val firstChar = price.substring(0, 2)
            val middleChar = price.substring(2, 5)
            val lastChar = price.substring(5, 8)
            price = "$firstChar.$middleChar.$lastChar"
        }
        else if (price.length == 9) {
            val firstChar = price.substring(0, 3)
            val middleChar = price.substring(3, 6)
            val lastChar = price.substring(6, 9)
            price = "$firstChar.$middleChar.$lastChar"
        }
        else if (price.length == 10) {
            val firstChar = price.substring(0, 1)
            val middleChar = price.substring(1, 4)
            val preLastChar = price.substring(4, 7)
            val lastChar = price.substring(7, 10)
            price = "$firstChar.$middleChar.$preLastChar.$lastChar"
        }
        else if (price.length == 11) {
            val firstChar = price.substring(0, 2)
            val middleChar = price.substring(2, 5)
            val preLastChar = price.substring(5, 8)
            val lastChar = price.substring(8, 11)
            price = "$firstChar.$middleChar.$preLastChar.$lastChar"
        }
        holder.tv_sumPrice.text  = "$price VNĐ"
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
                val intent = Intent(context,InvoiceWatingDetail_Activity::class.java)
                intent.putExtra("idBill",invoiceWating.idBill)
                intent.putExtra("date",invoiceWating.date)
                intent.putExtra("sumPrice",invoiceWating.sumPrice)
                intent.putExtra("status",invoiceWating.status)
                intent.putExtra("addressOrder",invoiceWating.addressOrder)
                context.startActivity(intent)
            }
            if (status.contains("Đã giao",true)){
                val intent = Intent(context,InvoiceAcceptedDetail_Activity::class.java)
                intent.putExtra("idBill",invoiceWating.idBill)
                context.startActivity(intent)
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