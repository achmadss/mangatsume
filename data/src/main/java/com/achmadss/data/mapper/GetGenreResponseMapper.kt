package com.achmadss.data.mapper

import com.achmadss.data.local.entities.Manga
import com.achmadss.data.remote.models.GetGenreResponse

fun GetGenreResponse.toGenre() = Manga.Genre(id, name, slug)