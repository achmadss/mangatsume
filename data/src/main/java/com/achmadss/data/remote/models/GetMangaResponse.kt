package com.achmadss.data.remote.models

import com.google.gson.annotations.SerializedName

data class GetMangaResponse(
    @SerializedName("comic") val comic: ComicResponse? = null,
)

data class ComicResponse(
    @SerializedName("hid") val hid: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("desc") val description: String? = null,
    @SerializedName("slug") val slug: String,
    @SerializedName("status") val status: Int? = null,
    @SerializedName("bayesian_rating") val rating: String? = null,
    @SerializedName("rating_count") val ratingCount: Int? = null,
    @SerializedName("recommendations") val recommendations: List<RecommendationResponse>? = null,
)

data class RecommendationResponse(
    @SerializedName("relates") val relates: RelatesResponse? = null,
)

data class RelatesResponse(
    @SerializedName("hid") val hid: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("slug") val slug: String? = null,
    @SerializedName("md_covers") val mdCovers: MDCoversResponse? = null,
)
