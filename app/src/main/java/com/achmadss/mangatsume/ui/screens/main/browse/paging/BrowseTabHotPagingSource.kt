package com.achmadss.mangatsume.ui.screens.main.browse.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.achmadss.domain.common.UseCaseResult
import com.achmadss.domain.enums.MangaTrendingType
import com.achmadss.domain.models.MangaModel
import com.achmadss.domain.usecases.GetHotUseCase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class BrowseTabHotPagingSource(
    private val getHotUseCase: GetHotUseCase,
    private val moreHotLoading: MutableStateFlow<Boolean>
): PagingSource<Int, MangaModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MangaModel> {
        return try {
            moreHotLoading.update { true }
            val nextPageNumber = params.key ?: 1
            val data = fetchData(nextPageNumber)
            val prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1
            val nextKey = if (data.isEmpty()) null else nextPageNumber + 1
            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        } finally {
            moreHotLoading.update { false }
        }
    }

    private suspend fun fetchData(nextPageNumber: Int): List<MangaModel> {
        var data: List<MangaModel> = listOf()
        coroutineScope {
            getHotUseCase.invoke(
                GetHotUseCase.GetHotUseCaseParam(MangaTrendingType.Hot, nextPageNumber)
            ).collect { result ->
                when (result) {
                    is UseCaseResult.Success -> data = result.data
                    else -> Unit
                }
            }
        }
        return data
    }

    override fun getRefreshKey(state: PagingState<Int, MangaModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}
