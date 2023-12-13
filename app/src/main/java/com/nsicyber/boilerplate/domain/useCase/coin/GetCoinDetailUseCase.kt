package com.nsicyber.boilerplate.domain.useCase.coin

import com.nsicyber.boilerplate.common.Resource
import com.nsicyber.boilerplate.data.remote.ApiService
import com.nsicyber.boilerplate.data.remote.dto.toCoinDetailModel
import com.nsicyber.boilerplate.domain.model.CoinDetailModel
import com.nsicyber.boilerplate.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject




class GetCoinDetailUseCase  @Inject constructor(
    private val repo: CoinRepository
) {
    operator fun invoke(coinId:String?): Flow<Resource<CoinDetailModel?>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(repo.getCoinById(coinId)?.toCoinDetailModel()))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
