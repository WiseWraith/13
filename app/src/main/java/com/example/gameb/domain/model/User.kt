package com.example.gameb.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name : String,
    val lastname : String,
    val email : String,
    val password : String,
    val coverResId : Int
)
