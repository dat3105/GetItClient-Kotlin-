package com.dinhconghien.getitapp.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dinhconghien.getitapp.Models.Cart
import com.dinhconghien.getitapp.R

class Cart_Adapter(val context: Context, val listCart : ArrayList<Cart>) : RecyclerView.Adapter<Cart_Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, null)
        return Cart_Adapter.ViewHolder(inflate)
    }


    override fun getItemCount(): Int {
        return listCart.size
    }

    override fun onBindViewHolder(holder: Cart_Adapter.ViewHolder, position: Int) {
        val cart = listCart.get(position)
        var count = 1

        holder.tv_nameLap.text = cart.nameLap
        holder.tv_brandLap.text = cart.brandLapName
        holder.tv_priceLap.text = "${cart.priceOfLap} VND"
        holder.tv_amountLap.text = "Còn ${cart.amountOfLap} sản phẩm "
        Glide.with(holder.itemView)
            .load(cart.avatarLap)
            .fitCenter()
            .into(holder.imv_avaLap)

        holder.linear_plus.setOnClickListener {
            val checkAmount = cart.amountOfLap

                if(count > checkAmount-1){
                    Toast.makeText(context,"Bạn đã nhập quá số lượng cho phép",Toast.LENGTH_LONG).show()
                }
                else{
                    count++
                    holder.tv_mountSell.text = count.toString()

            }
        }

        holder.linear_minus.setOnClickListener {
            if(count < 2){
                Toast.makeText(context,"Bạn không được nhập ít hơn 1 sản phẩm",Toast.LENGTH_LONG).show()
            }else{
                count--
                holder.tv_mountSell.text = count.toString()

            }
        }

        holder.imv_cancel.setOnClickListener {
            listCart.removeAt(position)
            notifyDataSetChanged()
            notifyItemRangeChanged(position,listCart.size)
            Toast.makeText(context,"Size listCart is ${listCart.size}",Toast.LENGTH_LONG).show()
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