package com.dinhconghien.getitapp.Models

import java.io.Serializable

class User() : Serializable {
    var email: String? = ""
    var phone: String? = ""
    var userName: String? = ""
    var password: String? = ""
    var role: String = "Customer"

    //    private var avaUser : String? = null
    var address: String? = null
    var isOnline: Boolean = false

    //sign up
    constructor(
        email: String, phone: String, userName: String, password: String,
        role: String = "Customer"
    ) : this() {
        this.email = email
        this.phone = phone
        this.userName = userName
        this.password = password
        this.role = role
    }

    //add address in cart
    constructor(address: String) : this() {
        this.address = address
    }

    //check login , if the user access without any other device that access previously -> access successfully
    //otherwise -> toast : "This account has been accessing in the other device"
    constructor(email: String, password: String, isOnline: Boolean) : this() {
        this.email = email
        this.password = password
        this.isOnline = isOnline
    }
}