package com.achmadss.data.mapper

import com.achmadss.data.common.Constants
import com.achmadss.data.local.entities.Manga
import com.achmadss.data.remote.models.GetHotResponse

fun List<GetHotResponse>.toMangas(): List<Manga> {
    return this.map {
        val coverKey = it.mdComics?.mdCovers?.firstOrNull()?.b2key
        Manga(
            title = it.mdComics?.title,
            slug = it.mdComics?.slug ?: "",
            coverUrl =
            if (coverKey != null) "${Constants.COVER_BASE_URL}$coverKey"
            else null
        )
    }
}