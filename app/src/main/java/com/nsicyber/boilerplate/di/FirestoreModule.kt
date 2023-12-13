package com.nsicyber.boilerplate.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nsicyber.boilerplate.data.repository.AuthRepositoryImpl
import com.nsicyber.boilerplate.data.repository.FirestoreRepositoryImpl
import com.nsicyber.boilerplate.domain.repository.AuthRepository
import com.nsicyber.boilerplate.domain.repository.FirestoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseUser() = Firebase.auth

    @Provides
    @Singleton
    fun provideFirebaseFirestore() = Firebase.firestore


    @Provides
    @Singleton
    fun provideFirestoreRepository(
        firebaseFirestore: FirebaseFirestore
    ): FirestoreRepository = FirestoreRepositoryImpl(firebaseFirestore)
}