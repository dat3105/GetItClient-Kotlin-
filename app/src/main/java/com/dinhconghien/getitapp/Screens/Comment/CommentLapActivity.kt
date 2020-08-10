package com.dinhconghien.getitapp.Screens.Comment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dinhconghien.getitapp.Adapter.CommentLap_Adapter
import com.dinhconghien.getitapp.Models.CommentLap
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.Screens.ListLapDetail.ListLapDetailActivity
import com.dinhconghien.getitapp.UI.DialogLoading
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_comment.*
import kotlinx.android.synthetic.main.activity_comment_lap.*
import kotlinx.android.synthetic.main.activity_list_lap_detail.*
import kotlinx.coroutines.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class CommentLapActivity : AppCompatActivity() {
    var checkAll = true
    var check1Star = false
    var check2Star = false
    var check3Star = false
    var check4Star = false
    var check5Star = false
    val DB_COMMENTLAP = FirebaseDatabase.getInstance().getReference("commentLap")
    var listCommentLap = ArrayList<CommentLap>()
    val TAG_GETCOMMENT = "DbError_getComment_commentLapScreen"
    lateinit var adapterCommentLap: CommentLap_Adapter
    var idLap = ""
    var idBrandLap = ""
    var listComment1Star = ArrayList<CommentLap>()
    var listComment2Star = ArrayList<CommentLap>()
    var listComment3Star = ArrayList<CommentLap>()
    var listComment4Star = ArrayList<CommentLap>()
    var listComment5Star = ArrayList<CommentLap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_lap)
        setSupportActionBar(toolbar_commentLap_activity)
        idLap = intent.getStringExtra("idLap")
        idBrandLap = intent.getStringExtra("idBrandLap")
        GlobalScope.launch(Dispatchers.Main) { updateUI() }

        layout_commentALLStar.setOnClickListener{
            GlobalScope.launch(Dispatchers.Main) {
                if (checkAll == false) {
                    setBackFroundButtonIsPress(layout_commentALLStar, tv_amountRatingAll_commentLap)
                    checkAll = true
                    updateUI()
                    setBackFroundButtonNotPress(layout_comment1Star, tv_1Star_commentLap)
                    check1Star = false
                    setBackFroundButtonNotPress(layout_comment2Star, tv_radioItem2)
                    check2Star = false
                    setBackFroundButtonNotPress(layout_comment3star, tv_radioItem3)
                    check3Star = false
                    setBackFroundButtonNotPress(layout_comment4Star, tv_radioItem4)
                    check4Star = false
                    setBackFroundButtonNotPress(layout_comment5Star, tv_radioItem5)
                    check5Star = false
                } }
        }

        layout_comment1Star.setOnClickListener{
           GlobalScope.launch(Dispatchers.Main) {
               if (check1Star == false) {
                   setBackFroundButtonIsPress(layout_comment1Star, tv_1Star_commentLap)
                   check1Star = true
                   setBackFroundButtonNotPress(
                       layout_commentALLStar,
                       tv_amountRatingAll_commentLap
                   )
                   checkAll = false
                   setBackFroundButtonNotPress(layout_comment2Star, tv_radioItem2)
                   check2Star = false
                   setBackFroundButtonNotPress(layout_comment3star, tv_radioItem3)
                   check3Star = false
                   setBackFroundButtonNotPress(layout_comment4Star, tv_radioItem4)
                   check4Star = false
                   setBackFroundButtonNotPress(layout_comment5Star, tv_radioItem5)
                   check5Star = false
                   getListComment()
                   if (listComment1Star.size == 0){
                       linear_commentLap.visibility = View.VISIBLE
                       adapterCommentLap.setListCommentLapNew(listComment1Star)
                   }else{
                       linear_commentLap.visibility = View.GONE
                       adapterCommentLap.setListCommentLapNew(listComment1Star)
                   }
               }
           }
        }

        layout_comment2Star.setOnClickListener{
            GlobalScope.launch(Dispatchers.Main) {
                if (check2Star == false) {
                    setBackFroundButtonIsPress(layout_comment2Star, tv_radioItem2)
                    check2Star = true
                    setBackFroundButtonNotPress(
                        layout_commentALLStar,
                        tv_amountRatingAll_commentLap
                    )
                    checkAll = false
                    setBackFroundButtonNotPress(layout_comment1Star, tv_1Star_commentLap)
                    check1Star = false
                    setBackFroundButtonNotPress(layout_comment3star, tv_radioItem3)
                    check3Star = false
                    setBackFroundButtonNotPress(layout_comment4Star, tv_radioItem4)
                    check4Star = false
                    setBackFroundButtonNotPress(layout_comment5Star, tv_radioItem5)
                    check5Star = false
                    getListComment()
                    if (listComment2Star.size == 0){
                        linear_commentLap.visibility = View.VISIBLE
                        adapterCommentLap.setListCommentLapNew(listComment2Star)
                    }else{
                        linear_commentLap.visibility = View.GONE
                        adapterCommentLap.setListCommentLapNew(listComment2Star)
                    }
                }
            }
        }

        layout_comment3star.setOnClickListener{
            GlobalScope.launch(Dispatchers.Main) {
                if (check3Star == false) {
                    setBackFroundButtonIsPress(layout_comment3star, tv_radioItem3)
                    check3Star = true
                    setBackFroundButtonNotPress(
                        layout_commentALLStar,
                        tv_amountRatingAll_commentLap
                    )
                    checkAll = false
                    setBackFroundButtonNotPress(layout_comment1Star, tv_1Star_commentLap)
                    check1Star = false
                    setBackFroundButtonNotPress(layout_comment2Star, tv_radioItem2)
                    check2Star = false
                    setBackFroundButtonNotPress(layout_comment4Star, tv_radioItem4)
                    check4Star = false
                    setBackFroundButtonNotPress(layout_comment5Star, tv_radioItem5)
                    check5Star = false
                    getListComment()
                    if (listComment3Star.size == 0){
                        linear_commentLap.visibility = View.VISIBLE
                        adapterCommentLap.setListCommentLapNew(listComment3Star)
                    }else{
                        linear_commentLap.visibility = View.GONE
                        adapterCommentLap.setListCommentLapNew(listComment3Star)
                    }
                }
            }
        }

        layout_comment4Star.setOnClickListener{
            GlobalScope.launch(Dispatchers.Main) {
                if (check4Star == false) {
                    setBackFroundButtonIsPress(layout_comment4Star, tv_radioItem4)
                    check4Star = true
                    setBackFroundButtonNotPress(
                        layout_commentALLStar,
                        tv_amountRatingAll_commentLap
                    )
                    checkAll = false
                    setBackFroundButtonNotPress(layout_comment1Star, tv_1Star_commentLap)
                    check1Star = false
                    setBackFroundButtonNotPress(layout_comment3star, tv_radioItem3)
                    check3Star = false
                    setBackFroundButtonNotPress(layout_comment2Star, tv_radioItem2)
                    check2Star = false
                    setBackFroundButtonNotPress(layout_comment5Star, tv_radioItem5)
                    check5Star = false

                    getListComment()
                    if (listComment4Star.size == 0){
                        linear_commentLap.visibility = View.VISIBLE
                        adapterCommentLap.setListCommentLapNew(listComment4Star)
                    }else{
                        linear_commentLap.visibility = View.GONE
                        adapterCommentLap.setListCommentLapNew(listComment4Star)
                    }
                }
            }
        }

        layout_comment5Star.setOnClickListener{
            GlobalScope.launch(Dispatchers.Main) {
                if (check5Star == false) {
                    setBackFroundButtonIsPress(layout_comment5Star, tv_radioItem5)
                    check5Star = true
                    setBackFroundButtonNotPress(
                        layout_commentALLStar,
                        tv_amountRatingAll_commentLap
                    )
                    checkAll = false
                    setBackFroundButtonNotPress(layout_comment1Star, tv_1Star_commentLap)
                    check1Star = false
                    setBackFroundButtonNotPress(layout_comment3star, tv_radioItem3)
                    check3Star = false
                    setBackFroundButtonNotPress(layout_comment4Star, tv_radioItem4)
                    check4Star = false
                    setBackFroundButtonNotPress(layout_comment2Star, tv_radioItem2)
                    check2Star = false
                    getListComment()
                    if (listComment5Star.size == 0){
                        linear_commentLap.visibility = View.VISIBLE
                        adapterCommentLap.setListCommentLapNew(listComment5Star)
                    }else{
                        linear_commentLap.visibility = View.GONE
                        adapterCommentLap.setListCommentLapNew(listComment5Star)
                    }
                }
            }
        }

        toolbar_commentLap_activity.setNavigationOnClickListener {
            val intent = Intent(this,ListLapDetailActivity::class.java)
            intent.putExtra("idLap",idLap)
            intent.putExtra("idBrandLap",idBrandLap)
            startActivity(intent)
            finish()
        }

        swipeRL_commentLap.setOnRefreshListener {
            swipeRL_commentLap.isRefreshing = false
            if (checkAll == true){
                GlobalScope.launch(Dispatchers.Main) { updateUI() }
            }
            else if (check1Star == true){
                if (listComment1Star.size == 0){
                    linear_commentLap.visibility = View.VISIBLE
                }else{
                    linear_commentLap.visibility = View.GONE
                    adapterCommentLap.setListCommentLapNew(listComment1Star)
                }
            }
            else if (check2Star == true){
                if (listComment2Star.size == 0){
                    linear_commentLap.visibility = View.VISIBLE
                }else{
                    linear_commentLap.visibility = View.GONE
                    adapterCommentLap.setListCommentLapNew(listComment2Star)
                }
            }
            else if (check3Star == true){
                if (listComment3Star.size == 0){
                    linear_commentLap.visibility = View.VISIBLE
                }else{
                    linear_commentLap.visibility = View.GONE
                    adapterCommentLap.setListCommentLapNew(listComment3Star)
                }
            }
            else if (check4Star == true){
                if (listComment4Star.size == 0){
                    linear_commentLap.visibility = View.VISIBLE
                }else{
                    linear_commentLap.visibility = View.GONE
                    adapterCommentLap.setListCommentLapNew(listComment4Star)
                }
            }
            else if (check5Star == true){
                if (listComment5Star.size == 0){
                    linear_commentLap.visibility = View.VISIBLE
                }else{
                    linear_commentLap.visibility = View.GONE
                    adapterCommentLap.setListCommentLapNew(listComment5Star)
                }
            }
        }
    }


    @SuppressLint("SetTextI18n")
    suspend fun updateUI() {
        val dialogLoading = DialogLoading(this)
        dialogLoading.show()
        adapterCommentLap = CommentLap_Adapter(listCommentLap,2)
        getListComment()
        delay(400L)
        if (listCommentLap.size == 0) {
           linear_commentLap.visibility = View.VISIBLE
            tv_amountRatingAll_commentLap.text =  "(${listCommentLap.size})"
            tv_radioItem2.text = "(${listCommentLap.size})"
            tv_radioItem3.text = "(${listCommentLap.size})"
            tv_radioItem4.text = "(${listCommentLap.size})"
            tv_radioItem5.text = "(${listCommentLap.size})"
            dialogLoading.dismiss()
        }else{
            linear_commentLap.visibility = View.GONE
            adapterCommentLap.setListCommentLapNew(listCommentLap)
            setListCommentItem()
            dialogLoading.dismiss()
        }

    }

    private fun setListCommentItem() {
        adapterCommentLap = CommentLap_Adapter(listCommentLap, 2)
        rcView_commentLap.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        rcView_commentLap.setHasFixedSize(true)
        rcView_commentLap.adapter = adapterCommentLap
        rcView_commentLap.scrollToPosition(listCommentLap.size -1)
    }

    private fun getListComment() {
        DB_COMMENTLAP.orderByChild("idLap").equalTo(idLap)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                @SuppressLint("LongLogTag")
                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG_GETCOMMENT, error.toString())
                }
                override fun onDataChange(snapshot: DataSnapshot) {
                    listCommentLap.clear()
                    listComment1Star.clear()
                    listComment2Star.clear()
                    listComment3Star.clear()
                    listComment4Star.clear()
                    listComment5Star.clear()
                    getCommentModelAll(snapshot)
                    getCommentModel1Star()
                    getCommentModel2Star()
                    getCommentModel3Star()
                    getCommentModel4Star()
                    getCommentModel5Star()

                }
            })
    }

    @SuppressLint("SetTextI18n")
    private fun getCommentModel1Star(){
        for (i in 0 until listCommentLap.size){
            if (listCommentLap[i].ratingByUser == 1){
               listComment1Star.add(listCommentLap[i])
            }
        }
        tv_1Star_commentLap.text = "(${listComment1Star.size})"
    }

    @SuppressLint("SetTextI18n")
    private fun getCommentModel2Star(){
        for (i in 0 until listCommentLap.size){
            if (listCommentLap[i].ratingByUser == 2){
                listComment2Star.add(listCommentLap[i])
            }
        }
        tv_radioItem2.text = "(${listComment2Star.size})"
    }

    @SuppressLint("SetTextI18n")
    private fun getCommentModel3Star(){
        for (i in 0 until listCommentLap.size){
            if (listCommentLap[i].ratingByUser == 3){
                listComment3Star.add(listCommentLap[i])
            }
        }
        tv_radioItem3.text = "(${listComment3Star.size})"
    }

    @SuppressLint("SetTextI18n")
    private fun getCommentModel4Star(){
        for (i in 0 until listCommentLap.size){
            if (listCommentLap[i].ratingByUser == 4){
                listComment4Star.add(listCommentLap[i])
            }
        }
        tv_radioItem4.text = "(${listComment4Star.size})"
    }

    @SuppressLint("SetTextI18n")
    private fun getCommentModel5Star(){
        for (i in 0 until listCommentLap.size){
            if (listCommentLap[i].ratingByUser == 5){
                listComment5Star.add(listCommentLap[i])
            }
        }
        tv_radioItem5.text = "(${listComment5Star.size})"
    }

    @SuppressLint("SetTextI18n")
    private fun getCommentModelAll(snapshot: DataSnapshot) {
        for (param in snapshot.children) {
            val commentModel = param.getValue(CommentLap::class.java)
            if (commentModel != null) {
                listCommentLap.add(commentModel)
                tv_amountRatingAll_commentLap.text = "(${listCommentLap.size})"
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun setBackFroundButtonNotPress(view: View, textView: TextView) {
        view.setBackgroundDrawable(resources.getDrawable(R.drawable.btnaccount_default))
        textView.setTextColor(Color.parseColor("#80000000"))
    }

    @SuppressLint("ResourceAsColor")
    private fun setBackFroundButtonIsPress(view: View, textView: TextView) {
        view.setBackgroundDrawable(resources.getDrawable(R.drawable.btn_comment_press))
        textView.setTextColor(Color.parseColor("#ffffff"))
    }


}