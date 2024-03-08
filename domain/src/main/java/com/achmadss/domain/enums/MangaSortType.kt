package com.achmadss.domain.enums

enum class MangaSortType(
    val type: String,
    val label: String,
) {
    MostViewed("view", "Most Viewed"),
    MostRecent("uploaded", "Most Recent"),
}