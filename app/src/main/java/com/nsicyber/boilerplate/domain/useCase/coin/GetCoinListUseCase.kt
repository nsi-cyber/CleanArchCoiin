package com.nsicyber.boilerplate.domain.useCase.coin

import com.nsicyber.boilerplate.common.Resource
import com.nsicyber.boilerplate.data.remote.ApiService
import com.nsicyber.boilerplate.data.remote.dto.CoinListDto
import com.nsicyber.boilerplate.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetCoinListUseCase @Inject constructor(
    private val repo: CoinRepository
) {
    operator fun invoke(): Flow<Resource<List<CoinListDto?>?>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(repo.getCoins()))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
