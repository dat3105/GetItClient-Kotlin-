package com.dinhconghien.getitapp.Screens.Message

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.dinhconghien.getitapp.Adapter.TablayoutViewPager_Adapter
import com.dinhconghien.getitapp.R
import com.google.android.material.tabs.TabLayout


class MessageFragment : Fragment() {
    lateinit var viewPager: ViewPager
    lateinit var tabLayout: TabLayout
    lateinit var tablayoutviewpagerAdapter: TablayoutViewPager_Adapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_message, container, false)
        anhXa(view)
        val fragmentManager =
            activity!!.supportFragmentManager
        tablayoutviewpagerAdapter = TablayoutViewPager_Adapter(fragmentManager)
        tablayoutviewpagerAdapter.addFragment(ListAdminFragment(), "Danh sách Admin")
        tablayoutviewpagerAdapter.addFragment(RoomChatFragment(), "Tin nhắn")
        viewPager.adapter = tablayoutviewpagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        return view
    }

    private fun anhXa(view: View) {
        viewPager = view.findViewById(R.id.viewPager_messageFragment)
        tabLayout = view.findViewById(R.id.tablayout_messageFragment)
    }
}