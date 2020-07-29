package com.dinhconghien.getitapp.Models

data class Bill(
    var idBill : String = "",
    var idUser : String = "",
    var date : String = "",
    var sumPrice : String = "",
    var status : String = "",
    var addressOrder : String = "",
    var listLapOrder : ArrayList<ListLaptop> = ArrayList(),
    var wasRated : Boolean = false,
    var idAdmin : String = "",
    var idPersonCancel : String = ""
) {
}