package com.nsicyber.boilerplate.domain.useCase.profile

import com.nsicyber.boilerplate.common.Resource
import com.nsicyber.boilerplate.data.remote.entity.CoinEntity
import com.nsicyber.boilerplate.domain.repository.FirestoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class AddToFavoriteUseCase  @Inject constructor(
    private val repo: FirestoreRepository
) {
    operator fun invoke(userUid:String,item: CoinEntity?): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(repo.addToFavorite(userUid,item!!)))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
