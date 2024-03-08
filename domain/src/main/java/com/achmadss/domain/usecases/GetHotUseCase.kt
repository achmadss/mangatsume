package com.achmadss.domain.usecases

import com.achmadss.data.common.APICallResult
import com.achmadss.data.common.safeAPICall
import com.achmadss.data.local.dao.MangaDao
import com.achmadss.data.mapper.toMangas
import com.achmadss.data.remote.services.MangaService
import com.achmadss.domain.common.FlowUseCase
import com.achmadss.domain.common.UseCaseResult
import com.achmadss.domain.enums.MangaTrendingType
import com.achmadss.domain.mapper.toMangaModel
import com.achmadss.domain.models.MangaModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetHotUseCase @Inject constructor(
    private val mangaService: MangaService,
    private val mangaDao: MangaDao,
): FlowUseCase<GetHotUseCase.GetHotUseCaseParam, List<MangaModel>>() {

    data class GetHotUseCaseParam(
        val type: MangaTrendingType = MangaTrendingType.Hot, // hot or latest
        val page: Int = 1,
    )

    override suspend fun await(
        parameters: GetHotUseCaseParam
    ): Flow<UseCaseResult<List<MangaModel>>> = flow {
        emit(UseCaseResult.Loading)
        val type = when(parameters.type) {
            MangaTrendingType.Hot -> "hot"
            else -> "new"
        }
        when(val result = safeAPICall { mangaService.getHot(order = type, page = parameters.page) }) {
            is APICallResult.Success -> {
                val data = result.data.toMangas()
                mangaDao.upsertManga(data)
                emit(UseCaseResult.Success(data.map { it.toMangaModel() }))
            }
            is APICallResult.Error -> throw result.error
        }
    }


}