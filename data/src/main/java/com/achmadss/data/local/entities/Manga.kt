package com.achmadss.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class Manga(
    val hid: String? = null,
    val title: String?,
    @PrimaryKey val slug: String,
    val description: String? = null,
    val status: Int = 5, // MangaStatus.Unknown
    val rating: String? = null,
    val ratingCount: Int? = null,
    val coverUrl: String? = null,
    val inLibrary: Boolean = false,
    val genres: List<Genre> = listOf(),
    val artists: List<String> = listOf(),
    val authors: List<String> = listOf(),
    val recommendations: List<Recommendation> = listOf(),
    val chapters: List<Chapter> = listOf()
) {
    data class Chapter(
        val title: String? = null,
        val chapterNumber: String? = null,
        val lastChapterNumberRead: String? = null,
        val finishedReading: Boolean = false,
        val scanlator: String = "Unknown",
        val uploadedAt: LocalDateTime? = null,
        val images: List<String> = listOf(),
    )
    data class Genre(
        val id: Int? = null,
        val name: String? = null,
        val slug: String,
    )
    data class Recommendation(
        val hid: String? = null,
        val title: String? = null,
        val slug: String? = null,
        val coverUrl: String? = null,
    )
}
