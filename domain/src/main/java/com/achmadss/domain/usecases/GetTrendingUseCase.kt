package com.achmadss.domain.usecases

import com.achmadss.data.common.APICallResult
import com.achmadss.data.common.safeAPICall
import com.achmadss.domain.enums.MangaTrendingType
import com.achmadss.data.local.dao.MangaDao
import com.achmadss.data.mapper.getAdaptedToAnime
import com.achmadss.data.mapper.getAllMangas
import com.achmadss.data.mapper.getMostFollowedNewComics
import com.achmadss.data.mapper.getMostRecentPopular
import com.achmadss.data.mapper.getPopularOngoing
import com.achmadss.data.mapper.getRecentlyAdded
import com.achmadss.data.remote.services.MangaService
import com.achmadss.domain.common.FlowUseCase
import com.achmadss.domain.common.UseCaseResult
import com.achmadss.domain.mapper.toMangaModel
import com.achmadss.domain.models.MangaModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTrendingUseCase @Inject constructor(
    private val mangaService: MangaService,
    private val mangaDao: MangaDao,
): FlowUseCase<Unit, Pair<MangaTrendingType, List<MangaModel>>>() {

    override suspend fun await(
        parameters: Unit
    ): Flow<UseCaseResult<Pair<MangaTrendingType, List<MangaModel>>>> = flow {
        emit(UseCaseResult.Loading)
        when(val result = safeAPICall { mangaService.getTrending() }) {
            is APICallResult.Success -> {
                val data = result.data
                mangaDao.upsertManga(data.getAllMangas())
                emit(UseCaseResult.Success(
                    Pair(
                        MangaTrendingType.MostRecentPopular7Days,
                        data.getMostRecentPopular(7).map { it.toMangaModel() }
                    ))
                )
                emit(UseCaseResult.Success(
                    Pair(
                        MangaTrendingType.MostRecentPopular30Days,
                        data.getMostRecentPopular(30).map { it.toMangaModel() }
                    ))
                )
                emit(UseCaseResult.Success(
                    Pair(
                        MangaTrendingType.MostRecentPopular90Days,
                        data.getMostRecentPopular(90).map { it.toMangaModel() }
                    ))
                )
                emit(UseCaseResult.Success(
                    Pair(
                        MangaTrendingType.MostFollowedNewComics7Days,
                        data.getMostFollowedNewComics(7).map { it.toMangaModel() }
                    ))
                )
                emit(UseCaseResult.Success(
                    Pair(
                        MangaTrendingType.MostFollowedNewComics30Days,
                        data.getMostFollowedNewComics(30).map { it.toMangaModel() }
                    ))
                )
                emit(UseCaseResult.Success(
                    Pair(
                        MangaTrendingType.MostFollowedNewComics90Days,
                        data.getMostFollowedNewComics(90).map { it.toMangaModel() }
                    ))
                )
                emit(UseCaseResult.Success(
                    Pair(
                        MangaTrendingType.PopularOngoing,
                        data.getPopularOngoing().map { it.toMangaModel() }
                    )
                ))
                emit(UseCaseResult.Success(
                    Pair(
                        MangaTrendingType.AdaptedToAnime,
                        data.getAdaptedToAnime().map { it.toMangaModel() }
                    )
                ))
                emit(UseCaseResult.Success(
                    Pair(
                        MangaTrendingType.RecentlyAdded,
                        data.getRecentlyAdded().map { it.toMangaModel() }
                    )
                ))
            }
            is APICallResult.Error -> throw result.error
        }
    }

}