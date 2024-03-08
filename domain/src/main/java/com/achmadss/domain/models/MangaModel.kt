package com.achmadss.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class MangaModel(
    val hid: String? = null,
    val title: String,
    val slug: String,
    val description: String? = null,
    val status: Int = 5, // MangaStatus.Unknown
    val rating: String? = null,
    val ratingCount: Int? = null,
    val coverUrl: String? = null,
    val inLibrary: Boolean = false,
    val genres: List<GenreModel> = listOf(),
    val artists: List<String> = listOf(),
    val authors: List<String> = listOf(),
    val recommendations: List<RecommendationModel> = listOf(),
    val chapters: List<ChapterModel> = listOf()
): Parcelable {
    @Parcelize
    data class ChapterModel(
        val title: String? = null,
        val chapterNumber: String? = null,
        val lastChapterNumberRead: String? = null,
        val finishedReading: Boolean = false,
        val scanlator: String = "Unknown",
        val uploadedAt: LocalDateTime? = null,
        val images: List<String> = listOf(),
    ): Parcelable
    @Parcelize
    data class GenreModel(
        val id: Int? = null,
        val name: String? = null,
        val slug: String,
    ): Parcelable
    @Parcelize
    data class RecommendationModel(
        val hid: String? = null,
        val title: String? = null,
        val slug: String? = null,
        val coverUrl: String? = null,
    ): Parcelable
}