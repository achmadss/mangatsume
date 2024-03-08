package com.achmadss.data.local.converters

import androidx.room.TypeConverter
import com.achmadss.data.local.entities.Manga
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenreConverter {

    val gson = Gson()

    @TypeConverter
    fun fromGenreList(genres: List<Manga.Genre>): String {
        return gson.toJson(genres)
    }

    @TypeConverter
    fun toGenreList(genresString: String): List<Manga.Genre> {
        val listType = object : TypeToken<List<Manga.Genre>>() {}.type
        return gson.fromJson(genresString, listType)
    }

}