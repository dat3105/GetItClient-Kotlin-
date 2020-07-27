package com.dinhconghien.getitapp.Models

import android.content.IntentSender

data class LastMessage(
    var idRoomChat: String = "",
    var idSender: String = "",
    var idReceiver: String = "",
    var message: String = "",
    var date: String = "",
    var wasRead : Boolean = false
) {
}