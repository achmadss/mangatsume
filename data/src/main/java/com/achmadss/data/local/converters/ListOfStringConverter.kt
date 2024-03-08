package com.achmadss.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListOfStringConverter {

    val gson = Gson()

    @TypeConverter
    fun fromChapterList(strings: List<String>): String {
        return gson.toJson(strings)
    }

    @TypeConverter
    fun toChapterList(strings: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(strings, listType)
    }

}