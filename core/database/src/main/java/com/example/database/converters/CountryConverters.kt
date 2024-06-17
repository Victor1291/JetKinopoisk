package com.example.database.converters

import androidx.room.TypeConverter
import com.example.database.modelDbo.CountriesDbo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CountryConverters {
    @TypeConverter
    fun mapListToString(value: List<CountriesDbo>): String {
        val gson = Gson()
        val type = object : TypeToken<List<CountriesDbo>>() {}.type
        return gson.toJson(value, type)
    }
    @TypeConverter
    fun mapStringToList(value: String): List<CountriesDbo> {
        val gson = Gson()
        val type = object : TypeToken<List<CountriesDbo>>() {}.type
        return gson.fromJson(value, type)
    }
}