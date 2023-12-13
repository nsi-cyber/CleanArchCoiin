package com.nsicyber.boilerplate.domain.model

import com.nsicyber.boilerplate.data.remote.entity.CoinEntity

data class CoinDetailModel(

    var id: String?,
    val symbol: String?,
    val name: String?,
    val hashingAlgorithm: String?,
    val lastUpdateDate: String?,
    val imageUrl: String?,
    val description: String?,
    val currentPrice: Float?,
    val changeIn24h: Float?,
)

fun CoinDetailModel.toCoinEntity():CoinEntity{
    return CoinEntity( id=this.id,
         symbol = this.symbol,
         name = this.name,
         lastUpdateDate = this.lastUpdateDate,
         imageUrl=this.imageUrl,
         currentPrice = this.currentPrice,
         changeIn24h = this.changeIn24h)
}
