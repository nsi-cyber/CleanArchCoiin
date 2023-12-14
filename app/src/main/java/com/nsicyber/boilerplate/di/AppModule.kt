package com.nsicyber.boilerplate.di

import android.content.Context
import com.nsicyber.boilerplate.common.Constants
import com.nsicyber.boilerplate.data.remote.ApiService
import com.nsicyber.boilerplate.data.repository.CoinRepositoryImpl
import com.nsicyber.boilerplate.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api: ApiService): CoinRepository {
        return CoinRepositoryImpl(api)
    }




}