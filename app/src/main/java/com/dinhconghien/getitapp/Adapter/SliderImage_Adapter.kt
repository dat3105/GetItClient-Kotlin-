package com.dinhconghien.getitapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.dinhconghien.getitapp.Models.SliderImage
import com.dinhconghien.getitapp.R
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderImage_Adapter(val context : Context ,val listSlider : ArrayList<SliderImage>) : SliderViewAdapter<SliderImage_Adapter.ViewHolder>() {
    /**
     * Create a new view holder
     *
     * @param parent wrapper view
     * @return view holder
     */
    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val inflate: View = LayoutInflater.from(parent!!.context).inflate(R.layout.item_image_slider, null)
        return ViewHolder(inflate)
    }

    fun deleteAllItem(){
        listSlider.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, position: Int) {
        val sliderItem: SliderImage = listSlider.get(position)
        viewHolder?.tv_description?.text = sliderItem.description
        Glide.with(viewHolder!!.itemView)
            .load(sliderItem.imageSlider)
            .fitCenter()
            .into(viewHolder.imv_slider)
    }

    override fun getCount(): Int {
      return listSlider.size
    }

    class ViewHolder(itemView : View) : SliderViewAdapter.ViewHolder(itemView){
         val imv_slider : ImageView = itemView.findViewById(R.id.iv_auto_image_slider)
         val tv_description : TextView = itemView.findViewById(R.id.tv_auto_image_slider)
    }
}