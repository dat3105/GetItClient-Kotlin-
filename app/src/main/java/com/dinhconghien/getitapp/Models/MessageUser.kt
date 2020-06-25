package com.dinhconghien.getitapp.Models

class MessageUser(
    var avaUser : Int? ,
    var unReadMesage : Int ,
    val userName : String ,
    var lastMessage : String ,
    var date : String,
    var isOnline : Boolean = true
) {
}