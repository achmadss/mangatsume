package com.achmadss.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.achmadss.data.local.converters.ChapterConverter
import com.achmadss.data.local.converters.GenreConverter
import com.achmadss.data.local.converters.ListOfStringConverter
import com.achmadss.data.local.converters.RecommendationConverter
import com.achmadss.data.local.dao.MangaDao
import com.achmadss.data.local.entities.Manga

@Database(
    entities = [
        Manga::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    ChapterConverter::class,
    GenreConverter::class,
    RecommendationConverter::class,
    ListOfStringConverter::class
)
abstract class MangatsumeDatabase: RoomDatabase() {

    abstract fun mangaDao(): MangaDao

    companion object {
        const val name = "mangatsume_db"
    }

}