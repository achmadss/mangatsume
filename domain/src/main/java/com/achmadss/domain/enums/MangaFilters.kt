package com.achmadss.domain.enums

data class MangaFilters(
    val genresIncludedSlug: List<String> = listOf(),
    val genresExcludedSlug: List<String> = listOf(),
)
