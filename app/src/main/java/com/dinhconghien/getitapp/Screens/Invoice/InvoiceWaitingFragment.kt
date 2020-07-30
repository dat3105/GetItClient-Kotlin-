package com.dinhconghien.getitapp.Screens.Invoice

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dinhconghien.getitapp.Adapter.Bill_Adapter
import com.dinhconghien.getitapp.Models.Bill
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.Utils.SharePreference_Utils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class InvoiceWaitingFragment : Fragment() {
    lateinit var adapterBill : Bill_Adapter
    lateinit var rcView_invoiceWating : RecyclerView
    lateinit var swipeRL_bill : SwipeRefreshLayout
    var listInvoice = ArrayList<Bill>()
        lateinit var idUser : String
    val DB_BILL = FirebaseDatabase.getInstance().getReference("bill")
    val TAG_GETBILL = "DbError_getBill_billWating"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_invoice_waiting, container, false)
        init(view)
        getInvoiceItem(view)
        swipeRL_bill.setOnRefreshListener {
            swipeRL_bill.isRefreshing = false
            getInvoiceItem(view)
        }
        return view
    }

    private fun init(view: View){
        rcView_invoiceWating = view.findViewById(R.id.rcView_invoiceWating)
        swipeRL_bill = view.findViewById(R.id.swipeRL_billWatingScreen)
        val utils = SharePreference_Utils(view.context)
        idUser = utils.getSession()
    }

    private fun getInvoiceItem(view: View){
        adapterBill = Bill_Adapter(view.context,listInvoice)
        rcView_invoiceWating.layoutManager = LinearLayoutManager(view.context,LinearLayoutManager.VERTICAL,false)
        rcView_invoiceWating.setHasFixedSize(true)
        rcView_invoiceWating.adapter = adapterBill
        getBillWating()
    }


    private fun getBillWating(){
        DB_BILL.orderByChild("idUser").equalTo(idUser)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                @SuppressLint("LongLogTag")
                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG_GETBILL,error.toString())
                }
                override fun onDataChange(snapshot: DataSnapshot) {
                    listInvoice.clear()
                    getBillModel(snapshot)
                }

            })
    }

    private fun getBillModel(snapshot: DataSnapshot){
        for(param in snapshot.children){
            val billModel = param.getValue(Bill::class.java)
            if (billModel != null){
                listInvoice.add(billModel)
                for (i in 0 until listInvoice.size){
                    if (listInvoice[i].status != "Đang chờ xác nhận"){
                        listInvoice.removeAt(i)
                    }
                }
                adapterBill.setListBill(listInvoice)
            }
        }
    }

}