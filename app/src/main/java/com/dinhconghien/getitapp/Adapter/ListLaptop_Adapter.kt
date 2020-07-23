package com.dinhconghien.getitapp.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.media.RatingCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dinhconghien.getitapp.Models.BrandLapName
import com.dinhconghien.getitapp.Models.ListLaptop
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.Screens.ListLapDetail.ListLapDetailActivity
import com.dinhconghien.getitapp.Screens.ListLaptop.ListLaptopActivity
import java.util.*
import kotlin.collections.ArrayList

class ListLaptop_Adapter(var context: Context , var listLap : ArrayList<ListLaptop>) :
    RecyclerView.Adapter<ListLaptop_Adapter.ViewHolder>() , Filterable {

    var listLapFilter = ArrayList<ListLaptop>()

    fun setListLapTop(listLap: ArrayList<ListLaptop>){
        this.listLap = listLap
        this.listLapFilter = listLap
        notifyDataSetChanged()
    }

    init {
        listLapFilter = listLap
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.item_listlaptop, null)
        context = parent.context
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
       return listLapFilter.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listLapItem = listLapFilter.get(position)
        holder.tv_nameLap.text = listLapItem.nameLap
        holder.tv_priceLap.text = "${listLapItem.priceLap} VNĐ"
        holder.tv_amountLap.text = "Số lượng : ${listLapItem.quantity} chiếc"
        Glide.with(holder.itemView)
            .load(listLapItem.avaLap)
            .fitCenter()
            .into(holder.imv_avaLap)

        holder.ratingLap.rating = listLapItem.rating.toFloat()

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ListLapDetailActivity::class.java)
            intent.putExtra("idLap",listLapItem.idLap)
            intent.putExtra("idBrandLap",listLapItem.idBrandLap)
            intent.putExtra("nameLap",listLapItem.nameLap)
            intent.putExtra("priceLap",listLapItem.priceLap)
            intent.putExtra("quantity",listLapItem.quantity)
            intent.putExtra("avaLap",listLapItem.avaLap)
            intent.putExtra("rating",listLapItem.rating)
            intent.putExtra("amountRating",listLapItem.amountRating)
            intent.putExtra("amountSell",listLapItem.amountSell)
            context.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imv_avaLap : ImageView = itemView.findViewById(R.id.imv_avatarLap_listLapItem)
        val tv_nameLap : TextView = itemView.findViewById(R.id.tv_nameLaptop_listLapItem)
        val tv_priceLap : TextView = itemView.findViewById(R.id.tv_priceLap_listLapItem)
        val tv_amountLap : TextView = itemView.findViewById(R.id.tv_amountLap_listLapItem)
        val ratingLap : androidx.appcompat.widget.AppCompatRatingBar = itemView.findViewById(R.id.ratingBar_listLapItem)
    }


    override fun getFilter(): Filter {
       return myFilter
    }

    val myFilter : Filter = object : Filter(){

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val charSearch = constraint.toString()
            if (charSearch.isEmpty()) {
                listLapFilter = listLap
            } else {
                val resultList = ArrayList<ListLaptop>()
                for (laptop in listLap) {
                    if (laptop.nameLap.toLowerCase().contains(charSearch.toLowerCase())) {
                        resultList.add(laptop)
                    }
                }
                listLapFilter = resultList
            }
            val filterResults = FilterResults()
            filterResults.values = listLapFilter
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listLapFilter = results?.values as ArrayList<ListLaptop>
                notifyDataSetChanged()
        }

    }

}