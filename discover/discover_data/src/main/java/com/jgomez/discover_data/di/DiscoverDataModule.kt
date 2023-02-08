package com.jgomez.discover_data.di

import com.jgomez.discover_data.network.TripApiService
import com.jgomez.discover_data.repository.TripRepositoryImpl
import com.jgomez.discover_domain.repository.TripRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object DiscoverDataModule {

    @Provides
    fun provideTripApiService(retrofit: Retrofit): TripApiService {
        return retrofit.create(TripApiService::class.java)
    }

    @Provides
    fun provideTripRepository(tripApiService: TripApiService): TripRepository {
    return TripRepositoryImpl(tripApiService)
    }

}