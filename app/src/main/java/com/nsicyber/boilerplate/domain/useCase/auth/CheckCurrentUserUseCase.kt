package com.nsicyber.boilerplate.domain.useCase.auth

import com.nsicyber.boilerplate.common.Resource
import com.nsicyber.boilerplate.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject



class CheckCurrentUserUseCase  @Inject constructor(
    private val repo: AuthRepository
) {
    operator fun invoke(): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(repo.getFirebaseUserUid()))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
