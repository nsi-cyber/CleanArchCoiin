package com.nsicyber.boilerplate.domain.repository

import com.google.firebase.auth.AuthResult


interface AuthRepository {

    suspend fun getFirebaseUserUid(): String

    suspend fun signUpWithEmailAndPassword(email: String, password: String)

    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthResult

    suspend fun isCurrentUserExist(): Boolean

    suspend fun signOut()

}