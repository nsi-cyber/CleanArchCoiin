package com.nsicyber.boilerplate.data.remote.entity

data class CoinEntity(val id:String? = null,
    val symbol: String? = null,
    val name: String? = null,
    val lastUpdateDate: String? = null,
    val imageUrl: String? = null,
    val currentPrice: Float? = null,
    val changeIn24h: Float? = null,
)