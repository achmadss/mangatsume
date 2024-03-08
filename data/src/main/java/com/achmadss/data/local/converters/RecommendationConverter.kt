package com.achmadss.data.local.converters

import androidx.room.TypeConverter
import com.achmadss.data.local.entities.Manga
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecommendationConverter {

    val gson = Gson()

    @TypeConverter
    fun fromRecommendationList(recommendations: List<Manga.Recommendation>): String {
        return gson.toJson(recommendations)
    }

    @TypeConverter
    fun toRecommendationList(recommendationsString: String): List<Manga.Recommendation> {
        val listType = object : TypeToken<List<Manga.Recommendation>>() {}.type
        return gson.fromJson(recommendationsString, listType)
    }

}