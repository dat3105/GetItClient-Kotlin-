package com.dinhconghien.getitapp.Repository

import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class UserRepository {
    private val dbReference = FirebaseDatabase.getInstance().getReference("user")
}