package com.dinhconghien.getitapp.Models

data class CommentLap(
    var idCommentLap: String = "",
    var idLap : String = "",
    var userName : String = "",
    var avaUser : String = "",
    var comment : String = "",
    var date : String = "",
    var ratingByUser : Int = 0
) {
}