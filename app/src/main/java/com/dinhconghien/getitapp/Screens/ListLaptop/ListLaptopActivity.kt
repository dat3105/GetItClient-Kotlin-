package com.dinhconghien.getitapp.Screens.ListLaptop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dinhconghien.getitapp.Adapter.ListLaptop_Adapter
import com.dinhconghien.getitapp.MainActivity
import com.dinhconghien.getitapp.Models.BrandLapName
import com.dinhconghien.getitapp.Models.ListLaptop
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.UI.DialogLoading
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_list_laptop.*
import kotlinx.coroutines.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ListLaptopActivity : AppCompatActivity() {

    private lateinit var adapterListLap: ListLaptop_Adapter
    private var dsLap = ArrayList<ListLaptop>()
    val DB_LAPTOP = FirebaseDatabase.getInstance().getReference("laptop")
    val DB_BRANDLAP = FirebaseDatabase.getInstance().getReference("brandLap")
    var idBrandLap = ""
    val TAG_ERROR_GETLAP = "DbErrorGetLap"
    val TAG_GETBRAND = "DbErrorGetBrandListLap"
    lateinit var job : Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_laptop)
        setSupportActionBar(toolbar_listLaptopScreen)
        job = Job()
        adapterListLap = ListLaptop_Adapter(this, dsLap)
       GlobalScope.launch(Dispatchers.Main) {
           updateUI()
       }
        nameLap_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                adapterListLap.myFilter.filter(newText)
                return false
            }
        })

        toolbar_listLaptopScreen.setNavigationOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        swipeRL_ListLapScreen.setOnRefreshListener {
            swipeRL_ListLapScreen.isRefreshing = false
            GlobalScope.launch(Dispatchers.Main) {
                updateUI()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    suspend fun updateUI(){
        val dialogLoading = DialogLoading(this)
        dialogLoading.show()
        delay(500L)
        getLapItem()
        getTitleBrand()
        dialogLoading.dismiss()
    }

    private fun getLap(){
        idBrandLap = intent.getStringExtra("idBrandLap")
        DB_LAPTOP.orderByChild("idBrandLap").equalTo(idBrandLap).addValueEventListener(object : ValueEventListener{

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG_ERROR_GETLAP,error.toString())
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                dsLap.clear()
              getLapModel(snapshot)
                adapterListLap.setListLapTop(dsLap)
                rcView_listLapScreen.scrollToPosition(dsLap.size - 1)
            }

        })
    }

    private fun getTitleBrand(){
        DB_BRANDLAP.orderByChild("idBrandLap").equalTo(idBrandLap).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
               Log.d(TAG_GETBRAND,error.toString())
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                for (param in snapshot.children){
                    val brandModel = param.getValue(BrandLapName::class.java)!!
                    toolbar_listLaptopScreen.title = brandModel.nameBrand
                }
            }
        })
    }

    fun getLapModel(snapshot: DataSnapshot){
        for (param in snapshot.children){
            val lapModel = param.getValue(ListLaptop::class.java)
            dsLap.add(lapModel!!)
        }
    }

    private fun getLapItem() {
        val gridLayoutManager =
           GridLayoutManager(this,2,LinearLayoutManager.VERTICAL,false)
        rcView_listLapScreen.layoutManager = gridLayoutManager
        rcView_listLapScreen.setHasFixedSize(true)
        getLap()
        rcView_listLapScreen.adapter = adapterListLap
    }

}