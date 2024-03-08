package com.achmadss.mangatsume.ui.root

import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.achmadss.mangatsume.constants.Routes
import com.achmadss.mangatsume.ui.screens.main.screenMain
import kotlinx.coroutines.CoroutineScope

data class Composition(
    val navController: NavHostController,
    val snackbarHostState: SnackbarHostState,
    val snackbarScope: CoroutineScope,
    val modalBottomSheetState: SheetState,
)

val LocalComposition = compositionLocalOf<Composition> { error("No Screen found") }

@Composable
fun AppRoot() {
    CompositionLocalProvider(
        LocalComposition provides Composition(
            navController = rememberNavController(),
            snackbarHostState = SnackbarHostState(),
            snackbarScope = rememberCoroutineScope(),
            modalBottomSheetState = rememberModalBottomSheetState(),
        )
    ) {
        NavHost(
            navController = LocalComposition.current.navController,
            startDestination = Routes.Main.MAIN,
        ) {
            screenMain()
        }
    }
}
