package com.achmadss.data.remote.models

import com.google.gson.annotations.SerializedName

data class GetSearchResponse(
    @SerializedName("hid") val hid: String? = null,
    @SerializedName("slug") val slug: String,
    @SerializedName("title") val title: String? = null,
    @SerializedName("cover_url") val coverUrl: String? = null
)
