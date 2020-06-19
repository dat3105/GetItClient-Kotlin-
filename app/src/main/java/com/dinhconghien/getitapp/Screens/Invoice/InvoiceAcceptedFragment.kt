package com.dinhconghien.getitapp.Screens.Invoice

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinhconghien.getitapp.Adapter.InvoiceAccepted_Adapter
import com.dinhconghien.getitapp.Adapter.InvoiceWating_Adapter
import com.dinhconghien.getitapp.Models.InvoiceAccepted
import com.dinhconghien.getitapp.Models.InvoiceWating
import com.dinhconghien.getitapp.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InvoiceAcceptedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InvoiceAcceptedFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var adapterInvoiceAccepted : InvoiceAccepted_Adapter
    lateinit var rcView_invoiceAccepted : RecyclerView
    lateinit var invoiceAccepted: InvoiceAccepted
    var listInvoiceAccepted= ArrayList<InvoiceAccepted>()

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
        invoiceAccepted = InvoiceAccepted("jcdwncbcJJNOJN","17/01/2020 09:30",12000000,"Đã xác nhận")
        listInvoiceAccepted.add(invoiceAccepted)

        invoiceAccepted = InvoiceAccepted("jcdwncbcJJNOJN","17/01/2020 09:30",12000000,"Đã xác nhận")
        listInvoiceAccepted.add(invoiceAccepted)

        invoiceAccepted = InvoiceAccepted("jcdwncbcJJNOJN","17/01/2020 09:30",12000000,"Đã xác nhận")
        listInvoiceAccepted.add(invoiceAccepted)

        invoiceAccepted = InvoiceAccepted("jcdwncbcJJNOJN","17/01/2020 09:30",12000000,"Đã xác nhận")
        listInvoiceAccepted.add(invoiceAccepted)
    }

    private fun getInvoiceItem(view: View){
        adapterInvoiceAccepted = InvoiceAccepted_Adapter(view.context,listInvoiceAccepted)
        rcView_invoiceAccepted.layoutManager = LinearLayoutManager(view.context,
            LinearLayoutManager.VERTICAL,false)
        rcView_invoiceAccepted.setHasFixedSize(true)
        rcView_invoiceAccepted.adapter = adapterInvoiceAccepted

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InvoiceAcceptedFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InvoiceAcceptedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}