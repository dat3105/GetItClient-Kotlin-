package com.dinhconghien.getitapp.Screens.Message

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinhconghien.getitapp.Adapter.UserMessage_Adapter
import com.dinhconghien.getitapp.Models.MessageUser
import com.dinhconghien.getitapp.R

class MessageFragment : Fragment() {
    lateinit var adapterMesUser : UserMessage_Adapter
    lateinit var rcView_Mes : RecyclerView
    lateinit var mesUser : MessageUser
    var listMes = ArrayList<MessageUser>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view = inflater.inflate(R.layout.fragment_message, container, false)
        anhXa(view)
        getMesItem(view)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        addMesItem()
    }
    private fun anhXa(view: View) {
        rcView_Mes = view.findViewById(R.id.rcView_messageScreen)
    }

    private fun addMesItem(){
        mesUser = MessageUser(R.drawable.tran,1,"Admin Công Hiến",
        "Hiến cu bự","01:30 26/06/2020")
        listMes.add(mesUser)

        mesUser = MessageUser(R.drawable.nganha,0,"Admin Ân Trần",
            "Ân cu tí","01:30 26/06/2020",false)
        listMes.add(mesUser)

        mesUser = MessageUser(R.drawable.sunna,4,"Admin Tằng Long",
            "Long chó điên","01:30 26/06/2020")
        listMes.add(mesUser)

        mesUser = MessageUser(null,0,"Admin Duy Nguyễn",
            "Duy cu bé =)))","01:30 26/06/2020",false)
        listMes.add(mesUser)
    }

    private fun getMesItem(view: View){
        adapterMesUser = UserMessage_Adapter(listMes)
        rcView_Mes.layoutManager = LinearLayoutManager(view.context,LinearLayoutManager.VERTICAL,false)
        rcView_Mes.setHasFixedSize(true)
        rcView_Mes.adapter = adapterMesUser
    }


}