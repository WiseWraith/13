package com.example.gameb.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Shop(
    val im_shopID:Int,
    val iconShopURL: String,
    val shopName : String,
    val price : Int,
    val available : String,
    val discount : String
)
