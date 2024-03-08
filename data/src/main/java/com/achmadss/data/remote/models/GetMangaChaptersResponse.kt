package com.achmadss.data.remote.models

import com.google.gson.annotations.SerializedName

data class GetMangaChaptersResponse(
    @SerializedName("chapters") val chapters: List<MangaChapterResponse>,
    @SerializedName("total") val total: Int? = null,
)

data class MangaChapterResponse(
    @SerializedName("chap") val chapterNumber: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("group_name") val scanlations: List<String> = listOf()
)
