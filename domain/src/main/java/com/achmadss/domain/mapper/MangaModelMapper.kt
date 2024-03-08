package com.achmadss.domain.mapper

import com.achmadss.data.local.entities.Manga
import com.achmadss.domain.models.MangaModel

fun Manga.toMangaModel() = MangaModel(
    hid,
    title = title ?: "",
    slug,
    description,
    status,
    rating,
    ratingCount,
    coverUrl,
    inLibrary,
    genres.map { it.toGenreModel() },
    artists, authors,
    recommendations.map { it.toRecommendationModel() },
    chapters.map { it.toChapterModel() }
)

fun Manga.Genre.toGenreModel() = MangaModel.GenreModel(
    id, name, slug
)

fun Manga.Chapter.toChapterModel() = MangaModel.ChapterModel(
    title, chapterNumber, lastChapterNumberRead, finishedReading, scanlator,
    uploadedAt, images
)

fun Manga.Recommendation.toRecommendationModel() = MangaModel.RecommendationModel(
    hid, title, slug, coverUrl
)