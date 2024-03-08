package com.achmadss.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.achmadss.data.local.entities.Manga

@Dao
interface MangaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertManga(manga: Manga)

    @Update
    suspend fun updateManga(manga: Manga)

    @Transaction
    suspend fun upsertManga(mangas: List<Manga>) {
        mangas.forEach { manga ->
            val existingManga = getMangaBySlug(manga.slug)
            if (existingManga == null) {
                insertManga(manga)
            } else {
                // Create a new Manga object from existing manga with inLibrary value excluded
                val updatedManga = manga.copy(inLibrary = existingManga.inLibrary)
                updateManga(updatedManga)
            }
        }
    }

    @Query("SELECT * FROM Manga")
    suspend fun getAllManga(): List<Manga> = listOf()

    @Query("SELECT * FROM Manga WHERE slug = :slug")
    suspend fun getMangaBySlug(slug: String): Manga?

    @Delete
    suspend fun deleteManga(manga: Manga)

}