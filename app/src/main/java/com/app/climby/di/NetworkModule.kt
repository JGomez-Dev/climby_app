package com.app.climby.di

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.app.climby.R
import com.app.climby.data.network.booking.BookingApiClient
import com.app.climby.data.network.province.ProvincesApiClient
import com.app.climby.data.network.school.SchoolApiClient
import com.app.climby.data.network.trip.TripApiClient
import com.app.climby.data.network.type.TypeApiClient
import com.app.climby.data.network.user.UserApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
        .baseUrl("https://climbyheroku.herokuapp.com/")
        //.baseUrl("http://192.168.1.40:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences? {
        return appContext.getSharedPreferences(appContext.getString(R.string.prefs_file), AppCompatActivity.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideTripApiClient(retrofit: Retrofit): TripApiClient {
        return retrofit.create(TripApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideUserApiClient(retrofit: Retrofit): UserApiClient {
        return retrofit.create(UserApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideProvincesApiClient(retrofit: Retrofit): ProvincesApiClient {
        return retrofit.create(ProvincesApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideSchoolApiClient(retrofit: Retrofit): SchoolApiClient {
        return retrofit.create(SchoolApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideTypesApiClient(retrofit: Retrofit): TypeApiClient {
        return retrofit.create(TypeApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideBookingApiClient(retrofit: Retrofit): BookingApiClient {
        return retrofit.create(BookingApiClient::class.java)
    }
}


