package com.achmadss.domain.enums

enum class MangaTrendingType(
    val label: String
) {
    Hot("Hot"),
    Latest("Latest"),
    PopularOngoing("Popular Ongoing"),
    MostRecentPopular7Days("Most Recent Popular"),
    MostRecentPopular30Days("Most Recent Popular"),
    MostRecentPopular90Days("Most Recent Popular"),
    MostFollowedNewComics7Days("Most Followed New Comics"),
    MostFollowedNewComics30Days("Most Followed New Comics"),
    MostFollowedNewComics90Days("Most Followed New Comics"),
    AdaptedToAnime("Adapted To Anime"),
    RecentlyAdded("Recently Added"),
}