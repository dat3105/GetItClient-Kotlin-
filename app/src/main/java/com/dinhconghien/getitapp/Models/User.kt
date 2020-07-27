package com.dinhconghien.getitapp.Models

import java.io.Serializable

class User() : Serializable{
    var userID : String? = ""
    var email : String? =""
    var userName : String? =""
    var phone : String? =""
    var password : String? =""
    var role : String = "Customer"
    var wasOnline : Boolean = false
    var avaUser : String? =""
    constructor( userID : String,email : String ="",userName : String , phone : String
                 ,password : String,role : String,wasOnline : Boolean,avaUser : String) : this(){
        this.userID = userID
        this.email = email
        this.userName = userName
        this.phone = phone
        this.password = password
        this.role = role
        this.wasOnline = wasOnline
        this.avaUser = avaUser
    }
}