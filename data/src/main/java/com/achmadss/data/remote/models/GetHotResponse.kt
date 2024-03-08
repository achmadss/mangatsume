package com.achmadss.data.remote.models

import com.google.gson.annotations.SerializedName

data class GetHotResponse(
    @SerializedName("md_comics") val mdComics: MDComicResponse? = null,
)

data class MDComicResponse(
    @SerializedName("hid") val hid: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("slug") val slug: String,
    @SerializedName("md_covers") val mdCovers: List<MDCoversResponse>? = null,
)