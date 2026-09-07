package com.example.edinburghtransportation.di

import com.example.edinburghtransportation.BuildConfig
import com.example.edinburghtransportation.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton
import javax.inject.Named

private const val BASE_URL = "https://transit.land/api/v2/rest/"
class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()

        val newUrl = originalRequest.url
            .newBuilder()
            .addQueryParameter(
                "api_key",
                BuildConfig.TRANSITLAND_API_KEY
            )
            .build()

        val newRequest = originalRequest
            .newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    @Named("Transit")
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }

    @Provides
    @Singleton
    @Named("Transit")
    fun provideRetrofit(
        @Named("Transit")  client: OkHttpClient,
        json: Json
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
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
    fun provideApiService(
        @Named("Transit") retrofit: Retrofit
    ): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}