package com.shu.network.di

import com.shu.network.ServiceMovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://kinopoiskapiunofficial.tech"

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {


    @Provides
    fun provideRetrofit(): ServiceMovieApi = Retrofit.Builder()
        .client(
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            })
                .addInterceptor(AuthorizationInterceptor())
                .build()
        )
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ServiceMovieApi::class.java)


}