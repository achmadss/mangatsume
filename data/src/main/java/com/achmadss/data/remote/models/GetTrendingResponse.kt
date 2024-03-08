package com.achmadss.data.remote.models

import com.google.gson.annotations.SerializedName

data class GetTrendingResponse(
    @SerializedName("rank") var rank: List<TrendingMangaResponse>? = null,
    @SerializedName("recentRank") var recentRank: List<TrendingMangaResponse>? = null,
    @SerializedName("trending") var trending: TrendingResponse? = null,
    @SerializedName("news") var news: List<TrendingMangaResponse>? = null,
    @SerializedName("topFollowNewComics") var topFollowNewComics: TrendingResponse? = null,
    @SerializedName("comicsByCurrentSeason") var adaptedToAnime: AdaptedToAnimeResponse? = null
)

data class TrendingMangaResponse(
    @SerializedName("title") var title: String? = null,
    @SerializedName("slug") var slug: String,
    @SerializedName("md_covers") var mdCovers: List<MDCoversResponse>? = null
)

data class TrendingResponse(
    @SerializedName("7") val in7Days: List<TrendingMangaResponse>? = null,
    @SerializedName("30") val in30Days: List<TrendingMangaResponse>? = null,
    @SerializedName("90") val in90Days: List<TrendingMangaResponse>? = null,
)

data class AdaptedToAnimeResponse(
    @SerializedName("year") val year: String? = null,
    @SerializedName("season") val season: String? = null,
    @SerializedName("data") val data: List<TrendingMangaResponse>? = null,
)