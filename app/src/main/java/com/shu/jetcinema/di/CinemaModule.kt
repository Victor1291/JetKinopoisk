package com.shu.jetcinema.di

import com.example.database.MovieDao
import com.example.search.domain.PagingSearchRepository
import com.example.bottom_sheet.domain.CollectionsRepository
import com.shu.detail_movie.domain.DetailRepository
import com.shu.detail_person.domain.PersonRepository
import com.shu.home.domain.HomeRepository
import com.shu.list_movies.domain.PagingRepository
import com.shu.network.ServiceMovieApi
import com.shu.network.repository.CollectionsRepositoryImpl
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
        api: ServiceMovieApi,
        dao: MovieDao
    ): HomeRepository {
        return HomeRepositoryImpl(api,dao)
    }

    @Provides
    fun providesDetailRepository(
        api: ServiceMovieApi,
        dao: MovieDao
    ): DetailRepository {
        return DetailRepositoryImpl(api,dao)
    }

    @Provides
    fun providesCollectionsRepository(
        dao: MovieDao
    ): CollectionsRepository {
        return CollectionsRepositoryImpl(dao)
    }

    @Provides
    fun providesPagingRepository(
        api: ServiceMovieApi,
        dao: MovieDao
    ): PagingRepository {
        return HomeRepositoryImpl(api,dao)
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