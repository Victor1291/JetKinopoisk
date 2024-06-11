package com.shu.jetcinema

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import com.shu.models.FilmVip


val FilmsParametersType: NavType<FilmVip?> = object : NavType<FilmVip?>(
    isNullableAllowed = false
) {
    override fun get(bundle: Bundle, key: String): FilmVip? {
        return bundle.getString(key)?.let { parseValue(it) }
    }

    override fun parseValue(value: String): FilmVip {
        return Gson().fromJson(value, FilmVip::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: FilmVip?) {
        bundle.putString(key, Gson().toJson(value))
    }

    override fun serializeAsValue(value: FilmVip?): String {
        return Gson().toJson(value)
    }
}