package com.example.edinburghtransportation.di

import com.example.edinburghtransportation.data.remote.VehicleApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Named
import javax.inject.Singleton


private const val VEHICLE_BASE_URL = "http://127.0.0.1:8080/"

@Module
@InstallIn(SingletonComponent::class)
object VehicleNetworkModule {

    @Provides
    @Singleton
    @Named("Vehicle")
    fun provideVehicleOkHttpClient(): OkHttpClient {

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Named("Vehicle")
    fun provideVehicleRetrofit(
        @Named("Vehicle") client: OkHttpClient,
        json: Json
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(VEHICLE_BASE_URL)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideVehicleApiService(
        @Named("Vehicle") retrofit: Retrofit
    ): VehicleApiService {
        return retrofit.create(VehicleApiService::class.java)
    }
}