package com.nsicyber.boilerplate.domain.useCase.auth


import com.google.firebase.auth.FirebaseUser
import com.nsicyber.boilerplate.common.Resource
import com.nsicyber.boilerplate.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject



class LoginUseCase  @Inject constructor(
    private val repo: AuthRepository
) {
     operator fun invoke(email:String,password: String): Flow<Resource<FirebaseUser>> = flow {
         try {
             emit(Resource.Loading())
             emit(Resource.Success(repo.signInWithEmailAndPassword(email, password).user!!))
         } catch(e: HttpException) {
             emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
         } catch(e: IOException) {
             emit(Resource.Error("Couldn't reach server. Check your internet connection."))
         }
     }
}
