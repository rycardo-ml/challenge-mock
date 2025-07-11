package com.example.challengemock.data.product.di

import com.example.challengemock.data.product.remote.ProductService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductNetworkModule {

    private val networkJson = Json { ignoreUnknownKeys = true }

    @Provides
    fun provideBaseUrl() = "https://dummyjson.com/"

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        url: String,
        loggingInterceptor: HttpLoggingInterceptor,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
        .client(OkHttpClient.Builder().addInterceptor(loggingInterceptor).build())
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit) : ProductService = retrofit.create(ProductService::class.java)

}