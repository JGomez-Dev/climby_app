package com.jgomez.discover_domain.di


import com.jgomez.discover_domain.repository.ProvinceRepository
import com.jgomez.discover_domain.repository.TripRepository
import com.jgomez.discover_domain.usecase.GetProvincesUseCase
import com.jgomez.discover_domain.usecase.GetCardsInformation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DiscoverDomainModule {

    @Provides
    fun provideGetTripsUseCase(tripRepository: TripRepository): GetCardsInformation {
        return GetCardsInformation(tripRepository)
    }

    @Provides
    fun provideGetProvincesUseCase(provinceRepository: ProvinceRepository): GetProvincesUseCase {
        return GetProvincesUseCase(provinceRepository)
    }

}