package com.dinhconghien.getitapp.Screens.Invoice

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinhconghien.getitapp.Adapter.Invoice_Adapter
import com.dinhconghien.getitapp.Models.Invoice
import com.dinhconghien.getitapp.R

class InvoiceAcceptedFragment : Fragment() {

    lateinit var adapterInvoiceAccepted : Invoice_Adapter
    lateinit var rcView_invoiceAccepted : RecyclerView
    lateinit var invoice: Invoice
    var listInvoice= ArrayList<Invoice>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_invoice_accepted, container, false)
        init(view)
        getInvoiceItem(view)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        addInvoiceItem()
    }

    private fun init(view: View){
        rcView_invoiceAccepted = view.findViewById(R.id.rcView_invoiceAccepted)
    }

    private fun addInvoiceItem(){
        invoice = Invoice("jcdwncbcJJNOJN","17/01/2020 09:30",12000000,"Đã giao")
        listInvoice.add(invoice)

        invoice = Invoice("jcdwncbcJJNOJN","17/01/2020 09:30",12000000,"Đã giao")
        listInvoice.add(invoice)

        invoice = Invoice("jcdwncbcJJNOJN","17/01/2020 09:30",12000000,"Đã giao")
        listInvoice.add(invoice)

        invoice = Invoice("jcdwncbcJJNOJN","17/01/2020 09:30",
            12000000,"Đã giao")
        listInvoice.add(invoice)
    }

    private fun getInvoiceItem(view: View){
        adapterInvoiceAccepted = Invoice_Adapter(view.context,listInvoice)
        rcView_invoiceAccepted.layoutManager = LinearLayoutManager(view.context,
            LinearLayoutManager.VERTICAL,false)
        rcView_invoiceAccepted.setHasFixedSize(true)
        rcView_invoiceAccepted.adapter = adapterInvoiceAccepted
    }


}