package com.nsicyber.boilerplate.data.remote.dto.coin

data class MarketData(
    val circulating_supply: Int,
    val current_price: CurrentPrice,
    val last_updated: String,
    val market_cap: MarketCap,
    val market_cap_change_24h: Float,
    val market_cap_change_24h_in_currency: MarketCapChange24hInCurrency,
    val market_cap_change_percentage_24h: Float,
    val market_cap_change_percentage_24h_in_currency: MarketCapChangePercentage24hInCurrency,
    val price_change_24h: Float,
    val price_change_24h_in_currency: PriceChange24hInCurrency,
    val price_change_percentage_24h: Float,
)