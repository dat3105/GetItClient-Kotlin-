package com.dinhconghien.getitapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dinhconghien.getitapp.Models.CommentLap
import com.dinhconghien.getitapp.R
import de.hdodenhof.circleimageview.CircleImageView

class CommentLap_Adapter(var listCommentLap : ArrayList<CommentLap>,var typeShow : Int)
    : RecyclerView.Adapter<CommentLap_Adapter.ViewHolder>() {

    fun setListCommentLapNew(listCommentLap: ArrayList<CommentLap>){
        this.listCommentLap = listCommentLap
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentLap_Adapter.ViewHolder {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.item_commentlap, null)
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
       if (typeShow == 1) return 1
        else return listCommentLap.size
    }

    override fun onBindViewHolder(holder: CommentLap_Adapter.ViewHolder, position: Int) {
        val commentModel = listCommentLap.get(position)
        holder.tv_userName.text = commentModel.userName
        holder.ratingBar.rating = commentModel.ratingByUser.toFloat()
        holder.tv_date.text = commentModel.date
        holder.tv_comment.text = commentModel.comment
        if(commentModel.avaUser != ""){
            Glide.with(holder.itemView).load(commentModel.avaUser).fitCenter().into(holder.imv_avaUser)
        }
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val imv_avaUser : CircleImageView = itemView.findViewById(R.id.imv_avaUser_itemCommentLap)
        val tv_userName : TextView = itemView.findViewById(R.id.tv_userName_itemCommentLapt)
        val ratingBar : RatingBar = itemView.findViewById(R.id.ratingBar_itemCommentLap)
        val tv_date  : TextView = itemView.findViewById(R.id.tv_date_itemCommentLap)
        val tv_comment    : TextView = itemView.findViewById(R.id.tv_comment_itemCommentLap)
    }
}