package com.achmadss.mangatsume.ui.screens.main.browse

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.achmadss.domain.common.UseCaseResult
import com.achmadss.domain.enums.MangaTrendingType
import com.achmadss.domain.models.MangaModel
import com.achmadss.domain.usecases.GetHotUseCase
import com.achmadss.domain.usecases.GetTrendingUseCase
import com.achmadss.mangatsume.common.BaseViewModel
import com.achmadss.mangatsume.common.UIState
import com.achmadss.mangatsume.common.update
import com.achmadss.mangatsume.ui.screens.main.browse.paging.BrowseTabHotPagingSource
import com.achmadss.mangatsume.ui.screens.main.browse.paging.BrowseTabLatestPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@Parcelize
data class BrowseTabUIState(
    val state: UIState = UIState.OnLoading,
    val pullRefresh: Boolean = false,
    val errorMessage: String = "Unknown Error",
    val selectedTrendingType: MangaTrendingType = MangaTrendingType.Hot,
    val popularOngoing: List<MangaModel> = listOf(),
    val mostRecentPopular7Days: List<MangaModel> = listOf(),
    val mostRecentPopular30Days: List<MangaModel> = listOf(),
    val mostRecentPopular90Days: List<MangaModel> = listOf(),
    val mostFollowedNewComics7Days: List<MangaModel> = listOf(),
    val mostFollowedNewComics30Days: List<MangaModel> = listOf(),
    val mostFollowedNewComics90Days: List<MangaModel> = listOf(),
    val adaptedToAnime: List<MangaModel> = listOf(),
    val recentlyAdded: List<MangaModel> = listOf(),
): Parcelable

@HiltViewModel
class BrowseTabViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getTrendingUseCase: GetTrendingUseCase,
    private val getHotUseCase: GetHotUseCase,
): BaseViewModel() {

    override val key: String get() = "BrowseTabUIState"

    val uiState = savedStateHandle.getStateFlow(key, BrowseTabUIState())

    private val _hotPagingData: MutableStateFlow<PagingData<MangaModel>> = MutableStateFlow(PagingData.empty())
    val hotPagingData = _hotPagingData.asStateFlow()

    private val _moreHotLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val moreHotLoading = _moreHotLoading.asStateFlow()

    private val _latestPagingData: MutableStateFlow<PagingData<MangaModel>> = MutableStateFlow(PagingData.empty())
    val latestPagingData = _latestPagingData.asStateFlow()

    private val _moreLatestLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val moreLatestLoading = _moreLatestLoading.asStateFlow()

    private var browseTabHotPagingSource: BrowseTabHotPagingSource? = null
    private var browseTabLatestPagingSource: BrowseTabLatestPagingSource? = null

    init {
        fetchHot()
        fetchLatest()
        handleFetchTrending()
    }

    private fun fetchHot() = viewModelScope.launch {
        Pager(PagingConfig(
            pageSize = 20,
            initialLoadSize = 1
        )) {
            createHotPagingSource()
        }.flow.cachedIn(viewModelScope).collect {
            _hotPagingData.value = it
        }
    }

    private fun fetchLatest() = viewModelScope.launch {
        Pager(PagingConfig(
            pageSize = 20,
            initialLoadSize = 1
        )) {
            createLatestPagingSource()
        }.flow.cachedIn(viewModelScope).collect {
            _latestPagingData.value = it
        }
    }

    private fun createHotPagingSource(): BrowseTabHotPagingSource {
        browseTabHotPagingSource = BrowseTabHotPagingSource(getHotUseCase, _moreHotLoading)
        return browseTabHotPagingSource!!
    }

    private fun createLatestPagingSource(): BrowseTabLatestPagingSource {
        browseTabLatestPagingSource = BrowseTabLatestPagingSource(getHotUseCase, _moreLatestLoading)
        return browseTabLatestPagingSource!!
    }

    fun handleRefresh(
        onAlreadyRefreshing: () -> Unit,
    ) {
        if (!uiState.value.pullRefresh) {
            if (uiState.value.state != UIState.OnLoading) {
                uiState.update(savedStateHandle, key) {
                    it.copy(pullRefresh = true)
                }
                browseTabHotPagingSource?.invalidate()
                browseTabLatestPagingSource?.invalidate()
                fetchHot()
                fetchLatest()
                handleFetchTrending()
                return
            }
        }
        onAlreadyRefreshing()
    }

    fun handleChangeTrendingType(type: MangaTrendingType) {
        uiState.update(savedStateHandle, key) {
            it.copy(
                selectedTrendingType = type
            )
        }
    }

    private fun handleFetchTrending() = viewModelScope.launch {
        getTrendingUseCase.invoke(Unit).collect { result ->
            when(result) {
                is UseCaseResult.Loading -> Unit
                is UseCaseResult.Success -> {
                    updateUIState(
                        stateFlow = uiState,
                        trendingType = result.data.first,
                        trendingList = result.data.second,
                    )
                }
                is UseCaseResult.Error -> {
                    uiState.update(savedStateHandle, key) {
                        it.copy(
                            state = UIState.OnError,
                            errorMessage = result.exception.message ?: "Unknown Error"
                        )
                    }
                }
            }
        }
    }

    private fun updateUIState(
        stateFlow: StateFlow<BrowseTabUIState>,
        trendingType: MangaTrendingType,
        trendingList: List<MangaModel>
    ) {
        val updatedUIState = when (trendingType) {
            MangaTrendingType.PopularOngoing ->
                stateFlow.value.copy(popularOngoing = trendingList)
            MangaTrendingType.MostRecentPopular7Days ->
                stateFlow.value.copy(mostRecentPopular7Days = trendingList)
            MangaTrendingType.MostRecentPopular30Days ->
                stateFlow.value.copy(mostRecentPopular30Days = trendingList)
            MangaTrendingType.MostRecentPopular90Days ->
                stateFlow.value.copy(mostRecentPopular90Days = trendingList)
            MangaTrendingType.MostFollowedNewComics7Days ->
                stateFlow.value.copy(mostFollowedNewComics7Days = trendingList)
            MangaTrendingType.MostFollowedNewComics30Days ->
                stateFlow.value.copy(mostFollowedNewComics30Days = trendingList)
            MangaTrendingType.MostFollowedNewComics90Days ->
                stateFlow.value.copy(mostFollowedNewComics90Days = trendingList)
            MangaTrendingType.AdaptedToAnime ->
                stateFlow.value.copy(adaptedToAnime = trendingList)
            MangaTrendingType.RecentlyAdded ->
                stateFlow.value.copy(recentlyAdded = trendingList)

            else -> stateFlow.value
        }

        stateFlow.update(savedStateHandle, key) {
            updatedUIState.copy(
                state = UIState.OnData,
                pullRefresh = false,
            )
        }
    }

}