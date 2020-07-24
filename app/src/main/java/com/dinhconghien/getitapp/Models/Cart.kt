package com.dinhconghien.getitapp.Models

data class Cart(
    var idUser : String = "",
    var addressOrder : String = "",
    var listLapOrder : ArrayList<ListLaptop> = ArrayList()
) {
}