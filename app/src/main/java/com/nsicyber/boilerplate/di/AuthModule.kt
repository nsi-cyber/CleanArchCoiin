package com.nsicyber.boilerplate.di

import com.google.firebase.auth.FirebaseAuth
import com.nsicyber.boilerplate.data.repository.AuthRepositoryImpl
import com.nsicyber.boilerplate.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.net.Authenticator
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuthenticator(
        firebaseAuth: FirebaseAuth
    ): AuthRepository = AuthRepositoryImpl(firebaseAuth)
}