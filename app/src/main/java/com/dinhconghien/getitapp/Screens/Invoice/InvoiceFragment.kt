package com.dinhconghien.getitapp.Screens.Invoice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.dinhconghien.getitapp.Adapter.TablayoutViewPager_Adapter
import com.dinhconghien.getitapp.R
import com.google.android.material.tabs.TabLayout

// TODO: Rename parameter arguments, choose names that match

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class InvoiceFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var viewPager: ViewPager
    lateinit var tabLayout: TabLayout
    lateinit var tablayoutviewpagerAdapter  : TablayoutViewPager_Adapter

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
        val view =  inflater.inflate(R.layout.fragment_invoice, container, false)
        anhXa(view)
        val fragmentManager =
            activity!!.supportFragmentManager
        tablayoutviewpagerAdapter = TablayoutViewPager_Adapter(fragmentManager)
        tablayoutviewpagerAdapter.addFragment(InvoiceWaitingFragment(), "Chờ xác nhận")
        tablayoutviewpagerAdapter.addFragment(InvoiceAcceptedFragment(), "Đã giao")
        viewPager.adapter = tablayoutviewpagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        return view
    }

    private fun anhXa(view: View) {
        viewPager = view.findViewById(R.id.viewPager_invoiceScreen)
        tabLayout = view.findViewById(R.id.tablayout_invoiceScreen)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InvoiceFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InvoiceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}