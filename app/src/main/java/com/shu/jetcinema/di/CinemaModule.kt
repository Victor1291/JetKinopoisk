package com.shu.jetcinema.di

import com.example.search.domain.PagingSearchRepository
import com.shu.detail_movie.domain.DetailRepository
import com.shu.detail_person.domain.PersonRepository
import com.shu.home.domain.HomeRepository
import com.shu.list_movies.domain.PagingRepository
import com.shu.network.ServiceMovieApi
import com.shu.network.repository.DetailRepositoryImpl
import com.shu.network.repository.HomeRepositoryImpl
import com.shu.network.repository.PagingSearchRepositoryImpl
import com.shu.network.repository.PersonRepositoryImpl
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

    @Provides
    fun providesPagingRepository(
        api: ServiceMovieApi
    ): PagingRepository {
        return HomeRepositoryImpl(api)
    }

    @Provides
    fun providesPersonRepository(
        api: ServiceMovieApi
    ): PersonRepository {
        return PersonRepositoryImpl(api)
    }

    @Provides
    fun providesPagingSearchRepository(
        api: ServiceMovieApi
    ): PagingSearchRepository {
        return PagingSearchRepositoryImpl(api)
    }
}