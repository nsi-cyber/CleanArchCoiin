package com.nsicyber.boilerplate.data.remote.dto

import com.nsicyber.boilerplate.data.remote.dto.coin.Description
import com.nsicyber.boilerplate.data.remote.dto.coin.Image
import com.nsicyber.boilerplate.data.remote.dto.coin.MarketData
import com.nsicyber.boilerplate.domain.model.CoinDetailModel

data class CoinDetailDto(
    val asset_platform_id: String,
    val categories: List<String>,
    val coingecko_rank: Int,
    val contract_address: String,
    val country_origin: String,
    val description: Description,
    val hashing_algorithm: String,
    val id: String,
    val image: Image,
    val last_updated: String,
    val market_data: MarketData,
    val name: String,
    val symbol: String,
)

fun CoinDetailDto.toCoinDetailModel(): CoinDetailModel {
    return CoinDetailModel(id=this.id,
        symbol = this.symbol,
        name = this.name,
        hashingAlgorithm = this.hashing_algorithm,
        lastUpdateDate = this.last_updated,
        imageUrl=this.image.large,
        description = this.description.en,
        currentPrice = this.market_data.current_price.usd,
        changeIn24h = this.market_data.price_change_percentage_24h
    )
}