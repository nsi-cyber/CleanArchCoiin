package com.nsicyber.boilerplate.domain.repository

import com.nsicyber.boilerplate.data.remote.dto.CoinDetailDto
import com.nsicyber.boilerplate.data.remote.dto.CoinListDto


interface CoinRepository {

    suspend fun getCoinById(coinId:String?) : CoinDetailDto?

    suspend fun getCoins() : List<CoinListDto?>?
}