package com.example.gameb.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Review (
    val user: User,
    val Date: String,
    val comment: String,
    val coverResId : Int
)
