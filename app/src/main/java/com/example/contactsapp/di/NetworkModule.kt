package com.example.contactsapp.di

import com.example.contactsapp.BuildConfig
import com.example.remote.api.ApiService
import com.example.remote.interceptors.ExceptionInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val CONNECTION_TIME_OUT = 2L
    private const val READ_TIME_OUT = 2L
    private const val WRITE_TIME_OUT = 2L
    private const val BASE_URL = "https://randomuser.me/api/?results=100"

    @Singleton
    @Provides
    fun provideNetworkClient(): Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.MINUTES)
                    .readTimeout(READ_TIME_OUT, TimeUnit.MINUTES)
                    .writeTimeout(WRITE_TIME_OUT, TimeUnit.MINUTES)
                    .addInterceptor(HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE))
                    .addInterceptor(ExceptionInterceptor())
                    .build()
            )
            .build()

    @Singleton
    @Provides
    fun provideContactsService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}