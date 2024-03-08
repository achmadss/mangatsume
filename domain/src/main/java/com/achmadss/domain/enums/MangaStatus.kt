package com.achmadss.domain.enums

enum class MangaStatus(
    val value: Int,
    val label: String,
) {
    Ongoing(1, "Ongoing"),
    Completed(2, "Completed"),
    Cancelled(3, "Cancelled"),
    Hiatus(4, "Hiatus"),
    Unknown(5, "Unknown")
}