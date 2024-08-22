package com.shu.network.repository

import com.example.database.MovieDao
import com.example.filter.domain.FilterRepository
import com.shu.models.ListFilters
import com.shu.network.models.filters.mapFromBd
import javax.inject.Inject

class FilterRepositoryImpl @Inject constructor(private val movieDao: MovieDao): FilterRepository {
    override suspend fun getFilters(): ListFilters {
        return movieDao.getFilters()?.mapFromBd() ?: ListFilters(genres = emptyList(), countries = emptyList())
    }
}