package com.achmadss.data.remote.models

import com.google.gson.annotations.SerializedName

data class GetGenreResponse(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("slug") val slug: String,
)
