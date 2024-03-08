package com.achmadss.data.remote.models

import com.google.gson.annotations.SerializedName

data class GetChapterResponse(
    @SerializedName("chapter") val chapter: ChapterResponse? = null
)

data class ChapterResponse(
    @SerializedName("title") val title: String? = null,
    @SerializedName("chap") val chapterNumber: String? = null,
    @SerializedName("images") val images: List<ChapterImageResponse> = listOf(),
)

data class ChapterImageResponse(
    @SerializedName("url") val url: String? = null,
)