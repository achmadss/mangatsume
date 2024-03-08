package com.achmadss.data.local.converters

import androidx.room.TypeConverter
import com.achmadss.data.local.entities.Manga
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ChapterConverter {

    val gson = Gson()

    @TypeConverter
    fun fromChapterList(chapters: List<Manga.Chapter>): String {
        return gson.toJson(chapters)
    }

    @TypeConverter
    fun toChapterList(chaptersString: String): List<Manga.Chapter> {
        val listType = object : TypeToken<List<Manga.Chapter>>() {}.type
        return gson.fromJson(chaptersString, listType)
    }

}