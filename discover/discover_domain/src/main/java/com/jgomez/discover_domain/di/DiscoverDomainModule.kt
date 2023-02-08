package com.jgomez.discover_domain.di


import com.jgomez.discover_domain.repository.ProvinceRepository
import com.jgomez.discover_domain.repository.TripRepository
import com.jgomez.discover_domain.usecase.GetProvincesUseCase
import com.jgomez.discover_domain.usecase.GetTripsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DiscoverDomainModule {

    @Provides
    fun provideGetTripsUseCase(tripRepository: TripRepository): GetTripsUseCase {
        return GetTripsUseCase(tripRepository)
    }

    @Provides
    fun provideGetProvincesUseCase(provinceRepository: ProvinceRepository): GetProvincesUseCase {
        return GetProvincesUseCase(provinceRepository)
    }

}