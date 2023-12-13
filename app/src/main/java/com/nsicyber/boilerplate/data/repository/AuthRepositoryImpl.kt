package com.nsicyber.boilerplate.data.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.nsicyber.boilerplate.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : AuthRepository {

    override suspend fun getFirebaseUserUid(): String = firebaseAuth.currentUser?.uid.orEmpty()

    override suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): AuthResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()




    override suspend fun isCurrentUserExist() = firebaseAuth.currentUser != null

    override suspend fun signOut() = firebaseAuth.signOut()

}