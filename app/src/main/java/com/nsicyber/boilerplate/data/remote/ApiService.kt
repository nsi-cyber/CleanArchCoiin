package com.nsicyber.boilerplate.data.remote

import com.nsicyber.boilerplate.data.remote.dto.CoinDetailDto
import com.nsicyber.boilerplate.data.remote.dto.CoinListDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("coins/{id}")
    suspend fun getCoinById(@Path("id") id:String?) : CoinDetailDto?

    @GET("coins/list")
    suspend fun getCoins() : List<CoinListDto?>?

}