package com.jgomez.discover_domain.di


import com.jgomez.discover_domain.repository.ProvinceRepository
import com.jgomez.discover_domain.repository.SharedPreferencesRepository
import com.jgomez.discover_domain.repository.TripRepository
import com.jgomez.discover_domain.usecase.GetProvincesUseCase
import com.jgomez.discover_domain.usecase.GetCardsInformation
import com.jgomez.discover_domain.usecase.GetTripDetail
import com.jgomez.discover_domain.usecase.GetUserInformation
import com.jgomez.discover_domain.usecase.SetUserInformation
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

    @Provides
    fun provideGetTripDetail(tripRepository: TripRepository): GetTripDetail {
        return GetTripDetail(tripRepository)
    }

    @Provides
    fun provideSetUserInformation(provinceRepository: SharedPreferencesRepository): SetUserInformation {
        return SetUserInformation(provinceRepository)
    }

    @Provides
    fun provideGetUserInformation(provinceRepository: SharedPreferencesRepository): GetUserInformation {
        return GetUserInformation(provinceRepository)
    }


}