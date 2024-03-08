package com.achmadss.data.mapper

import android.util.Log
import com.achmadss.data.common.Constants.COVER_BASE_URL
import com.achmadss.data.local.entities.Manga
import com.achmadss.data.remote.models.GetTrendingResponse
import com.achmadss.data.remote.models.TrendingMangaResponse

fun GetTrendingResponse.getMostRecentPopular(days: Int): List<Manga> {
    return when(days) {
        7 -> this.trending?.in7Days?.toManga() ?: listOf()
        30 -> this.trending?.in30Days?.toManga() ?: listOf()
        90 -> this.trending?.in90Days?.toManga() ?: listOf()
        else -> emptyList()
    }
}

fun GetTrendingResponse.getMostFollowedNewComics(days: Int): List<Manga> {
    return when(days) {
        7 -> this.topFollowNewComics?.in7Days?.toManga() ?: listOf()
        30 -> this.topFollowNewComics?.in30Days?.toManga() ?: listOf()
        90 -> this.topFollowNewComics?.in90Days?.toManga() ?: listOf()
        else -> emptyList()
    }
}

fun GetTrendingResponse.getPopularOngoing(): List<Manga> {
    return this.rank?.toManga() ?: listOf()
}

fun GetTrendingResponse.getRecentlyAdded(): List<Manga> {
    return this.news?.toManga() ?: listOf()
}

fun GetTrendingResponse.getAdaptedToAnime(): List<Manga> {
    return this.adaptedToAnime?.data?.toManga() ?: listOf()
}

fun GetTrendingResponse.getAllMangas(): List<Manga> {
    return getPopularOngoing() +
            getRecentlyAdded() +
            getMostRecentPopular(7) +
            getMostRecentPopular(30) +
            getMostRecentPopular(90) +
            getMostFollowedNewComics(7) +
            getMostFollowedNewComics(30) +
            getMostFollowedNewComics(90)
}

private fun List<TrendingMangaResponse>.toManga(): List<Manga> {
    return this.map {
        val coverKey = it.mdCovers?.firstOrNull()?.b2key
        Manga(
            title = it.title,
            slug = it.slug,
            coverUrl =
            if (coverKey != null) "$COVER_BASE_URL$coverKey"
            else null
        )
    }
}