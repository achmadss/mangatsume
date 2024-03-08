package com.achmadss.data.remote.services

import com.achmadss.data.remote.models.GetChapterResponse
import com.achmadss.data.remote.models.GetGenreResponse
import com.achmadss.data.remote.models.GetHotResponse
import com.achmadss.data.remote.models.GetMangaChaptersResponse
import com.achmadss.data.remote.models.GetMangaResponse
import com.achmadss.data.remote.models.GetSearchResponse
import com.achmadss.data.remote.models.GetTrendingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MangaService {

    @GET("genre")
    suspend fun getGenres(): Response<List<GetGenreResponse>>

    @GET("top")
    suspend fun getTrending(
        @Query("type") type: String = "trending",
        @Query("accept_mature_content") acceptMatureContent: Boolean = true
    ): Response<GetTrendingResponse>

    @GET("chapter")
    suspend fun getHot(
        @Query("page") page: Int,
        @Query("order") order: String,
        @Query("tachiyomi") tachiyomi: Boolean = true,
        @Query("accept_erotic_content") acceptEroticContent: Boolean = true,
    ): Response<List<GetHotResponse>>

    @GET("chapter/{hid}/")
    suspend fun getChapterInfo(
        @Query("tachiyomi") tachiyomi: Boolean = true,
        @Path("hid") hid: String,
    ): Response<GetChapterResponse>

    @GET("v1.0/search")
    suspend fun searchManga(
        @Query("q") query: String = "",
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("sort") sort: String = "view",
        @Query("genres") genresIncludedSlug: List<String> = listOf(),
        @Query("excluded") genresExcludedSlug: List<String> = listOf(),
        @Query("showall") showAll: Boolean = true,
        @Query("tachiyomi") tachiyomi: Boolean = true,
        @Query("t") t: Boolean = false,
    ): Response<List<GetSearchResponse>>

    @GET("comic/{slug}/")
    suspend fun getMangaDetail(
        @Path("slug") slug: String,
        @Query("tachiyomi") tachiyomi: Boolean = true,
    ): Response<GetMangaResponse>

    @GET("comic/{hid}/chapters")
    suspend fun getMangaChapters(
        @Path("hid") hid: String,
        @Query("limit") limit: Int = Int.MAX_VALUE,
        @Query("lang") language: String = "en"
    ): Response<GetMangaChaptersResponse>

}