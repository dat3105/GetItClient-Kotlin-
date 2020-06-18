package com.dinhconghien.getitapp.Screens.Invoice

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinhconghien.getitapp.Adapter.BrandLapName_Adapter
import com.dinhconghien.getitapp.Adapter.InvoiceWating_Adapter
import com.dinhconghien.getitapp.Models.BrandLapName
import com.dinhconghien.getitapp.Models.InvoiceWating
import com.dinhconghien.getitapp.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class InvoiceWaitingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var adapterInvoiceWating : InvoiceWating_Adapter
    lateinit var rcView_invoiceWating : RecyclerView
    lateinit var invoiceWating: InvoiceWating
    var listInvoiceWating = ArrayList<InvoiceWating>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_invoice_waiting, container, false)
        init(view)
        getInvoiceItem(view)
        return view
    }

    private fun init(view: View){
        rcView_invoiceWating = view.findViewById(R.id.rcView_invoiceWating)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        addInvoiceItem()
    }

    private fun addInvoiceItem(){
        invoiceWating = InvoiceWating("jcdwncbcJJNOJN","17/01/2020",12000000,"Đang chờ xác nhận")
        listInvoiceWating.add(invoiceWating)

        invoiceWating = InvoiceWating("jcdwncbcJJNOJN","17/01/2020",12000000,"Đang chờ xác nhận")
        listInvoiceWating.add(invoiceWating)

        invoiceWating = InvoiceWating("jcdwncbcJJNOJN","17/01/2020",12000000,"Đang chờ xác nhận")
        listInvoiceWating.add(invoiceWating)

        invoiceWating = InvoiceWating("jcdwncbcJJNOJN","17/01/2020",12000000,"Đang chờ xác nhận")
        listInvoiceWating.add(invoiceWating)
    }

    private fun getInvoiceItem(view: View){
        adapterInvoiceWating = InvoiceWating_Adapter(view.context,listInvoiceWating)
        rcView_invoiceWating.layoutManager = LinearLayoutManager(view.context,LinearLayoutManager.VERTICAL,false)
        rcView_invoiceWating.setHasFixedSize(true)
        rcView_invoiceWating.adapter = adapterInvoiceWating

    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InvoiceWaitingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}