package com.achmadss.mangatsume.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.achmadss.mangatsume.constants.Routes
import com.achmadss.mangatsume.ui.root.LocalComposition
import com.achmadss.mangatsume.ui.screens.main.browse.BrowseTab
import com.achmadss.mangatsume.ui.screens.main.browse.BrowseTabViewModel
import com.achmadss.mangatsume.ui.screens.main.library.LibraryTab
import com.achmadss.mangatsume.ui.screens.main.library.LibraryTabViewModel
import com.achmadss.mangatsume.ui.screens.main.updates.UpdatesTab
import com.achmadss.mangatsume.ui.screens.main.updates.UpdatesTabViewModel
import com.achmadss.mangatsume.ui.theme.MaterialColors

@SuppressLint("UnspecifiedRegisterReceiverFlag")
fun NavGraphBuilder.screenMain() {
    composable(
        route = Routes.Main.MAIN,
    ) {
        val viewModel = hiltViewModel<ScreenMainViewModel>()
        val uiState = viewModel.uiState.collectAsState().value

        val tabs = mutableListOf(
            LibraryTab(hiltViewModel<LibraryTabViewModel>()),
            UpdatesTab(hiltViewModel<UpdatesTabViewModel>()),
            BrowseTab(hiltViewModel<BrowseTabViewModel>()),
        )

        ScreenMain(
            tabs = tabs,
            selectedTab = tabs.firstOrNull { uiState.currentTabName == it.name } ?: tabs.first(),
            onClick = {
                viewModel.handleTabChange(it)
            }
        )

    }
}

@Composable
fun ScreenMain(
    tabs: List<Tab>,
    selectedTab: Tab,
    onClick: (String?) -> Unit,
) {
    Scaffold(
        snackbarHost = { SnackbarHost(LocalComposition.current.snackbarHostState) },
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            selectedTab.TopBar()
        },
        bottomBar = {
            NavigationBar(
                tonalElevation = 0.dp // remove primary translucent
            ) {
                tabs.forEach {
                    NavigationBarItem(
                        selected = it.name == selectedTab.name,
                        onClick = { onClick(it.name) },
                        icon = { it.BottomBarIcon(selectedTab.name) },
                        label = { it.BottomBarLabel() }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box {
            selectedTab.Content(paddingValues)
        }
    }
}
