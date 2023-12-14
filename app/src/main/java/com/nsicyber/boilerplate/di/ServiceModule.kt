package com.nsicyber.boilerplate.di

import com.nsicyber.boilerplate.domain.useCase.coin.GetCoinDetailUseCase
import com.nsicyber.boilerplate.domain.useCase.profile.AddToFavoriteUseCase
import com.nsicyber.boilerplate.domain.useCase.profile.GetUserFavoritesUseCase
import com.nsicyber.boilerplate.service.BackgroundTrackerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import javax.inject.Singleton

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {
    @Singleton
    @Provides
    fun provideBackgroundTrackerService(): BackgroundTrackerService = BackgroundTrackerService()
}