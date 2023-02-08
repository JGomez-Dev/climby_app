package com.jgomez.publish_domain.di

import com.jgomez.publish_domain.repository.ProvinceRepository
import com.jgomez.publish_domain.usecase.GetProvincesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object PublishDomainModule {

    @Provides
    fun provideGetProvincesUseCase(provinceRepository: ProvinceRepository): GetProvincesUseCase {
        return GetProvincesUseCase(provinceRepository)
    }

}