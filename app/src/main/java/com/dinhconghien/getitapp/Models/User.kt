package com.dinhconghien.getitapp.Models

import android.provider.ContactsContract
import java.io.Serializable


class User() : Serializable{
     var userID : String = ""
     var email : String =""
     var userName : String =""
     var phone : String =""
     var password : String =""
     var role : String = "Customer"
     var wasOnline : Boolean = false
     var wasFirstBuy : Boolean = true
     var userAddress : String? = null
     var avaUser : String? = null

    constructor(userID:String,email: String,userName:String,phone:String,password:String
                ,role:String,wasOnline : Boolean,wasFirstBuy:Boolean) : this(){
        this.userID =userID
        this.email = email
        this.userName = userName
        this.phone = phone
        this.password = password
        this.role = role
        this.wasOnline = wasOnline
        this.wasFirstBuy = wasFirstBuy
    }
}