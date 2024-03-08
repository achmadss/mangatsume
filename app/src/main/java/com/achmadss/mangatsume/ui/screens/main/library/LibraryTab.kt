package com.achmadss.mangatsume.ui.screens.main.library

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.achmadss.mangatsume.R
import com.achmadss.mangatsume.common.UIState
import com.achmadss.mangatsume.ui.components.EmptyScreen
import com.achmadss.mangatsume.ui.components.FastScrollLazyVerticalGrid
import com.achmadss.mangatsume.ui.components.LoadingScreen
import com.achmadss.mangatsume.ui.root.LocalComposition
import com.achmadss.mangatsume.ui.screens.main.Tab
import com.achmadss.mangatsume.ui.components.SearchTopAppBar

class LibraryTab(
    private val viewModel: LibraryTabViewModel
): Tab {

    override val name: String? get() = this::class.simpleName

    @Composable
    override fun TopBar() {
        // this is the image loader
        // val imageLoader = LocalContext.current.imageLoader
        SearchTopAppBar(
            title = stringResource(id = R.string.label_tab_library),
            onSearch = {
                viewModel.handleSearch(it)
            },
            actions = {
                IconButton(onClick = {
                    // this is how to remove cache
                    // imageLoader.diskCache?.remove(key) // key = imageUrl
                    viewModel.handleUpdateLibrary()
                }) {
                    Icon(imageVector = Icons.Default.FilterList, contentDescription = "")
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "")
                }
            }
        )
    }

    @Composable
    override fun BottomBarLabel() {
        Text(
            text = stringResource(id = R.string.label_tab_library),
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }

    @OptIn(ExperimentalAnimationGraphicsApi::class)
    @Composable
    override fun BottomBarIcon(tabName: String?) {
        val image = AnimatedImageVector
            .animatedVectorResource(id = R.drawable.anim_library_enter)
        Icon(
            painter = rememberAnimatedVectorPainter(
                animatedImageVector = image,
                atEnd = name == tabName
            ),
            contentDescription = ""
        )
    }

    @Composable
    override fun Content(paddingValues: PaddingValues) {
        val uiState = viewModel.uiState.collectAsState().value
        val snackbarHostState = LocalComposition.current.snackbarHostState
        val scope = rememberCoroutineScope()
        when(uiState.state) {
            UIState.OnLoading -> LoadingScreen()
            UIState.OnData -> {
                FastScrollLazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    columns = GridCells.Fixed(3)
                ) {
//                    items(uiState.data) { data ->
//                        Box {
//                            MangaCover(
//                                slug = data.first,
//                                title = data.second,
//                                imageUrl = data.third,
//                                onClick = {
//                                    scope.launch {
//                                        snackbarHostState.showSnackbar(
//                                            message = data.second,
//                                        )
//                                    }
//                                }
//                            )
//                        }
//                    }
                    item(span = { GridItemSpan(maxLineSpan) }) { }
                }
            }
            UIState.OnError -> {
                EmptyScreen(
                    stringResId = R.string.information_empty_library
                )
            }
        }
    }

}