package com.nsicyber.boilerplate.data.repository

import com.nsicyber.boilerplate.data.remote.ApiService
import com.nsicyber.boilerplate.data.remote.dto.CoinDetailDto
import com.nsicyber.boilerplate.data.remote.dto.CoinListDto
import com.nsicyber.boilerplate.domain.repository.CoinRepository
import javax.inject.Inject


class CoinRepositoryImpl @Inject constructor(
    private val api: ApiService
) : CoinRepository {

    override suspend fun getCoins(): List<CoinListDto?>? {
        return api.getCoins()
    }

    override suspend fun getCoinById(coinId: String?): CoinDetailDto? {
        return api.getCoinById(coinId)
    }
}