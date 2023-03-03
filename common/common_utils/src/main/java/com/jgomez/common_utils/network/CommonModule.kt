package com.jgomez.common_utils.network

import com.jgomez.common_utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CommonModule {


    private val httpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val original = chain.request()
        val request = original.newBuilder()
                .header("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlZHVhcmRvdGVzdEBnbWFpbC5jb20iLCJleHAiOjE2Nzk5NjMxNDksIm5hbWUiOiJlZHVhcmRvdGVzdEBnbWFpbC5jb20ifQ.7-BBcms_pkc1aYO55Oud3Uz31DhtEgN6YsaU7LrN25Q")
            .build()
        chain.proceed(request)
    }.build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).client(httpClient).addConverterFactory(GsonConverterFactory.create()).build()
    }

}