package com.dinhconghien.getitapp.Models

data class ListLaptop(
    val idLap : String ="",
    var idBrandLap : String = "",
    var nameLap : String = "",
    var priceLap : Long = 0,
    var quantity : Int =0,
    var avaLap : String ="",
    var rating : Int = 0,
    var amountRating : Int = 0,
    var amountSell : Int = 0,
    var nameBrand : String = "",
    var amountInCart : Int = 1,
    var wasRated : Boolean = false
) {
}