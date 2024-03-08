package com.achmadss.mangatsume.ui.screens.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable

interface Tab {
    val name: String?
    @Composable fun TopBar()
    @Composable fun BottomBarLabel()
    @Composable fun BottomBarIcon(tabName: String?)
    @Composable fun Content(paddingValues: PaddingValues)

}