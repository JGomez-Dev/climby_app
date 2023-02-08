package com.jgomez.publish_data.di

import com.jgomez.publish_data.network.ProvinceApiService
import com.jgomez.publish_data.repository.ProvinceRepositoryImpl
import com.jgomez.publish_domain.repository.ProvinceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object PublishDataModule {

    @Provides
    fun provideProvincesApiService(retrofit: Retrofit):ProvinceApiService{
        return  retrofit.create(ProvinceApiService::class.java)
    }

    @Provides
    fun provideProvinceRepository(provinceApiService: ProvinceApiService): ProvinceRepository {
    return ProvinceRepositoryImpl(provinceApiService)
    }

}