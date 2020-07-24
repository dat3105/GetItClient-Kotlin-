package com.dinhconghien.getitapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dinhconghien.getitapp.Models.Bot
import com.dinhconghien.getitapp.Models.Cart
import com.dinhconghien.getitapp.Models.ListLaptop
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.UI.CustomToast

class Cart_Adapter(var context: Context, var lisCart : ArrayList<ListLaptop>) : RecyclerView.Adapter<Cart_Adapter.ViewHolder>() {

    fun setList(lisCart: ArrayList<ListLaptop>){
        this.lisCart = lisCart
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, null)
        return Cart_Adapter.ViewHolder(inflate)
    }


    override fun getItemCount(): Int {
        return lisCart.size
    }

    override fun onBindViewHolder(holder: Cart_Adapter.ViewHolder, position: Int) {
        val lapOrder = lisCart.get(position)
        var count = 1

        holder.tv_nameLap.text = lapOrder.nameLap
        holder.tv_brandLap.text = lapOrder.nameBrand
        var price = lapOrder.priceLap.toString()
        if (price.length == 7){
            val firstChar = price.substring(0,1)
            val middleChar = price.substring(1,4)
            val lastChar = price.substring(3,6)
            price = "$firstChar.$middleChar.$lastChar"
        }else if (price.length == 8){
            val firstChar = price.substring(0,2)
            val middleChar = price.substring(2,5)
            val lastChar = price.substring(4,7)
            price = "$firstChar.$middleChar.$lastChar"
        }
        holder.tv_priceLap.text = "${price} VND"
        holder.tv_amountLap.text = "Còn ${lapOrder.quantity} sản phẩm "
        Glide.with(holder.itemView)
            .load(lapOrder.avaLap)
            .fitCenter()
            .into(holder.imv_avaLap)

        holder.linear_plus.setOnClickListener {
            val checkAmount = lapOrder.quantity

                if(count > checkAmount-1){
                    CustomToast.makeText(context,"Bạn đã nhập quá số lượng cho phép",Toast.LENGTH_LONG,2)
                        ?.show()
                }
                else{
                    count++
                    holder.tv_mountSell.text = count.toString()
            }
        }

        holder.linear_minus.setOnClickListener {
            if(count < 2){
                CustomToast.makeText(context,"Bạn không được nhập ít hơn 1 sản phẩm",Toast.LENGTH_LONG,2)
                    ?.show()
            }else{
                count--
                holder.tv_mountSell.text = count.toString()
            }
        }

        holder.imv_cancel.setOnClickListener {
            val listAfterDelete : MutableList<ListLaptop> = lisCart.toMutableList().apply {
                removeAt(position)
            }
            setList(listAfterDelete as ArrayList<ListLaptop>)
            notifyDataSetChanged()
            notifyItemRangeChanged(position,lisCart.size)
        }
    }

    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
       val imv_avaLap : ImageView = itemView.findViewById(R.id.imv_avatarCart_cartItem)
        val tv_nameLap : TextView = itemView.findViewById(R.id.tv_nameLapTop_cartItem)
        val tv_brandLap : TextView = itemView.findViewById(R.id.tv_nameBrand_cartItem)
        val tv_priceLap : TextView = itemView.findViewById(R.id.tv_priceLap_cartItem)
        val linear_plus : LinearLayout = itemView.findViewById(R.id.linear_plus)
        val linear_minus : LinearLayout = itemView.findViewById(R.id.linear_minus)
        val tv_mountSell : TextView = itemView.findViewById(R.id.tv_amountSell_cartItem)
        val tv_amountLap : TextView = itemView.findViewById(R.id.tv_amountLap_cartItem)
        val imv_cancel : ImageView = itemView.findViewById(R.id.imv_cancel_cartItem)
    }
}