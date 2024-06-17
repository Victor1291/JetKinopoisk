package com.example.database.converters

import androidx.room.TypeConverter
import com.example.database.modelDbo.GenresDbo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenresConverters {
    @TypeConverter
    fun mapListToString(value: List<GenresDbo>): String {
        val gson = Gson()
        val type = object : TypeToken<List<GenresDbo>>() {}.type
        return gson.toJson(value, type)
    }
    @TypeConverter
    fun mapStringToList(value: String): List<GenresDbo> {
        val gson = Gson()
        val type = object : TypeToken<List<GenresDbo>>() {}.type
        return gson.fromJson(value, type)
    }
}