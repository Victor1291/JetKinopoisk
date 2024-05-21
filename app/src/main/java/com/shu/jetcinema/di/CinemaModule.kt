package com.shu.jetcinema.di

import com.shu.network.repository.*
import com.shu.network.ServiceMovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CinemaModule {

    @Provides
    fun providesRepository(
        api: ServiceMovieApi
    ): HomeRepository {
        return HomeRepositoryImpl(api)
    }

    @Provides
    fun providesDetailRepository(
        api: ServiceMovieApi
    ): DetailRepository {
        return DetailRepositoryImpl(api)
    }

}