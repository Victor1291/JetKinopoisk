package com.example.filter.domain

import com.shu.models.ListFilters

interface FilterRepository {

    suspend fun getFilters(): ListFilters

}