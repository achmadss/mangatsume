package com.achmadss.mangatsume.ui.screens.main.browse

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ChipDefaults.filterChipColors
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Whatshot
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.paging.compose.collectAsLazyPagingItems
import com.achmadss.domain.enums.MangaTrendingType
import com.achmadss.domain.models.MangaModel
import com.achmadss.mangatsume.R
import com.achmadss.mangatsume.common.UIState
import com.achmadss.mangatsume.ui.components.LoadingScreen
import com.achmadss.mangatsume.ui.components.SearchTopAppBar
import com.achmadss.mangatsume.ui.components.cover.MangaCover
import com.achmadss.mangatsume.ui.components.cover.MangaCoverStyle
import com.achmadss.mangatsume.ui.root.LocalComposition
import com.achmadss.mangatsume.ui.screens.main.Tab
import com.achmadss.mangatsume.ui.theme.MaterialColors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BrowseTab(
    private val viewModel: BrowseTabViewModel
): Tab {
    override val name: String? get() = this::class.simpleName

    private var snackbarJob : Job? = null

    private fun handleAlreadyRunning(
        scope: CoroutineScope,
        snackbarHostState: SnackbarHostState
    ) {
        snackbarJob?.cancel()
        snackbarJob = scope.launch {
            snackbarHostState.showSnackbar(
                "Already refreshing"
            )
        }
    }

    @Composable
    override fun TopBar() {
        val snackbarHostState = LocalComposition.current.snackbarHostState
        val scope = LocalComposition.current.snackbarScope
        SearchTopAppBar(
            title = stringResource(id = R.string.label_tab_browse),
            onSearch = {

            },
            actions = {
                IconButton(onClick = {
                    viewModel.handleRefresh {
                        handleAlreadyRunning(scope, snackbarHostState)
                    }
                }) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "")
                }
            }
        )
    }

    @Composable
    override fun BottomBarLabel() {
        Text(
            text = stringResource(id = R.string.label_tab_browse),
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }

    @OptIn(ExperimentalAnimationGraphicsApi::class)
    @Composable
    override fun BottomBarIcon(tabName: String?) {
        val image = AnimatedImageVector
            .animatedVectorResource(id = R.drawable.anim_browse_enter)
        Icon(
            painter = rememberAnimatedVectorPainter(
                animatedImageVector = image,
                atEnd = name == tabName
            ),
            contentDescription = ""
        )
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content(paddingValues: PaddingValues) {
        val uiState = viewModel.uiState.collectAsState().value
        val hotPagingData = viewModel.hotPagingData.collectAsLazyPagingItems()
        val moreHotLoading = viewModel.moreHotLoading.collectAsState().value
        val latestPagingData = viewModel.latestPagingData.collectAsLazyPagingItems()
        val moreLatestLoading = viewModel.moreLatestLoading.collectAsState().value
        val snackbarHostState = LocalComposition.current.snackbarHostState
        val scope = LocalComposition.current.snackbarScope
        val pullRefreshState = rememberPullRefreshState(
            refreshing = uiState.pullRefresh,
            onRefresh = {
                viewModel.handleRefresh {
                    handleAlreadyRunning(scope, snackbarHostState)
                }
            }
        )
        var trendingChipHeight by remember { mutableStateOf(IntSize(0,0)) }
        var contentWidth by remember { mutableStateOf(IntSize(0,0)) }
        val types = MangaTrendingType.entries.toMutableList()
        types.removeAll(
            listOf(
                MangaTrendingType.MostRecentPopular30Days,
                MangaTrendingType.MostRecentPopular90Days,
                MangaTrendingType.MostFollowedNewComics30Days,
                MangaTrendingType.MostFollowedNewComics90Days,
            )
        )

        fun getMangaModelsFromType(): List<MangaModel> {
            return when(uiState.selectedTrendingType) {
                MangaTrendingType.PopularOngoing -> uiState.popularOngoing
                MangaTrendingType.MostRecentPopular7Days -> uiState.mostRecentPopular7Days
                MangaTrendingType.MostRecentPopular30Days -> uiState.mostRecentPopular30Days
                MangaTrendingType.MostRecentPopular90Days -> uiState.mostRecentPopular90Days
                MangaTrendingType.MostFollowedNewComics7Days -> uiState.mostFollowedNewComics7Days
                MangaTrendingType.MostFollowedNewComics30Days -> uiState.mostFollowedNewComics30Days
                MangaTrendingType.MostFollowedNewComics90Days -> uiState.mostFollowedNewComics90Days
                MangaTrendingType.AdaptedToAnime -> uiState.adaptedToAnime
                MangaTrendingType.RecentlyAdded -> uiState.recentlyAdded
                else -> listOf()
            }
        }

        Box(
            modifier = Modifier
                .pullRefresh(pullRefreshState)
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val filterChipColors = filterChipColors(
                    backgroundColor = MaterialColors.Neutral10,
                    selectedBackgroundColor = MaterialColors.Secondary30
                )
                Row(
                    modifier = Modifier
                        .onGloballyPositioned {
                            trendingChipHeight = it.size
                        }
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    types.forEach {
                        FilterChip(
                            selected = uiState.selectedTrendingType == it,
                            colors = filterChipColors,
                            onClick = {
                                viewModel.handleChangeTrendingType(it)
                            }
                        ) {
                            if (it == MangaTrendingType.Hot) {
                                Icon(
                                    modifier = Modifier.size(16.dp),
                                    imageVector = Icons.Outlined.Whatshot,
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                            }
                            Text(
                                text = it.label,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                }
                Spacer(modifier = Modifier.height(4.dp))
                Divider()
                when(uiState.state) {
                    UIState.OnLoading -> LoadingScreen()
                    UIState.OnData -> {
                        AnimatedContent(
                            targetState = uiState.selectedTrendingType,
                            label = "",
                            transitionSpec = {
                                fadeIn().togetherWith(fadeOut())
                            }
                        ) { type ->
                            LazyVerticalGrid(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .onGloballyPositioned {
                                        contentWidth = it.size
                                    }
                                    .padding(
                                        start = paddingValues
                                            .calculateStartPadding(LocalLayoutDirection.current)
                                            .plus(16.dp),
                                        end = paddingValues
                                            .calculateEndPadding(LocalLayoutDirection.current)
                                            .plus(16.dp),
                                    ),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                columns = GridCells.Fixed(3)
                            ) {
                                when(type) {
                                    MangaTrendingType.Hot -> {
                                        item(span = { GridItemSpan(maxLineSpan) }) { }
                                        items(hotPagingData.itemCount) {
                                            hotPagingData[it]?.let {
                                                MangaCover(
                                                    modifier = Modifier
                                                        .width(
                                                            contentWidth.toSize().width
                                                                .minus(32)
                                                                .div(3)
                                                                .minus(12).dp
                                                        ),
                                                    style = MangaCoverStyle.Inside,
                                                    inLibrary = it.inLibrary,
                                                    slug = it.slug,
                                                    title = it.title,
                                                    imageUrl = it.coverUrl,
                                                    onClick = {
                                                        // TODO handle manga click
                                                    }
                                                )
                                            }
                                        }
                                        if (moreHotLoading) {
                                            item(span = {
                                                GridItemSpan(maxLineSpan)
                                            }) {
                                                Box(modifier = Modifier
                                                    .fillMaxWidth()
                                                    .wrapContentSize(Alignment.Center)
                                                ) {
                                                    CircularProgressIndicator()
                                                }
                                            }
                                        }
                                        item(span = { GridItemSpan(maxLineSpan) }) { }
                                    }
                                    MangaTrendingType.Latest -> {
                                        item(span = { GridItemSpan(maxLineSpan) }) { }
                                        items(latestPagingData.itemCount) {
                                            latestPagingData[it]?.let {
                                                MangaCover(
                                                    modifier = Modifier
                                                        .width(
                                                            contentWidth.toSize().width
                                                                .minus(32)
                                                                .div(3)
                                                                .minus(12).dp
                                                        ),
                                                    style = MangaCoverStyle.Inside,
                                                    inLibrary = it.inLibrary,
                                                    slug = it.slug,
                                                    title = it.title,
                                                    imageUrl = it.coverUrl,
                                                    onClick = {
                                                        // TODO handle manga click
                                                    }
                                                )
                                            }
                                        }
                                        if (moreLatestLoading) {
                                            item(span = {
                                                GridItemSpan(maxLineSpan)
                                            }) {
                                                Box(modifier = Modifier
                                                    .fillMaxWidth()
                                                    .wrapContentSize(Alignment.Center)
                                                ) {
                                                    CircularProgressIndicator()
                                                }
                                            }
                                        }
                                        item(span = { GridItemSpan(maxLineSpan) }) { }
                                    }
                                    else -> {
                                        val data = getMangaModelsFromType()
                                        if (data.isNotEmpty()) {
                                            item(span = { GridItemSpan(maxLineSpan) }) { }
                                            items(data) {
                                                MangaCover(
                                                    modifier = Modifier
                                                        .width(
                                                            contentWidth.toSize().width
                                                                .minus(32)
                                                                .div(3)
                                                                .minus(12).dp
                                                        ),
                                                    style = MangaCoverStyle.Inside,
                                                    inLibrary = it.inLibrary,
                                                    slug = it.slug,
                                                    title = it.title,
                                                    imageUrl = it.coverUrl,
                                                    onClick = {
                                                        // TODO handle manga click
                                                    }
                                                )
                                            }
                                            item(span = { GridItemSpan(maxLineSpan) }) { }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    UIState.OnError -> {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Text(
                                modifier = Modifier.align(Alignment.Center),
                                text = uiState.errorMessage,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
            PullRefreshIndicator(
                uiState.pullRefresh,
                pullRefreshState,
                Modifier
                    .align(Alignment.TopCenter)
                    .padding(
                        top = 52.dp
                    ),
                backgroundColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background
            )
        }
    }

}