package com.achmadss.mangatsume.ui.screens.main.updates

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.achmadss.mangatsume.R
import com.achmadss.mangatsume.services.UpdateService
import com.achmadss.mangatsume.services.UpdateService.Companion.COUNTER_UPDATE_ACTION_START
import com.achmadss.mangatsume.services.UpdateService.Companion.COUNTER_UPDATE_ACTION_STOP
import com.achmadss.mangatsume.services.UpdateService.Companion.COUNTER_VALUE_EXTRA
import com.achmadss.mangatsume.ui.root.LocalComposition
import com.achmadss.mangatsume.ui.screens.main.Tab
import kotlinx.coroutines.launch

class UpdatesTab(
    private val viewModel: UpdatesTabViewModel
): Tab {

    override val name: String? get() = this::class.simpleName

    @Composable
    override fun TopBar() {

    }

    @Composable
    override fun BottomBarLabel() {
        Text(
            text = stringResource(id = R.string.label_tab_updates),
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }

    @OptIn(ExperimentalAnimationGraphicsApi::class)
    @Composable
    override fun BottomBarIcon(tabName: String?) {
        val uiState = viewModel.uiState.collectAsState().value
        BadgedBox(
            badge = {
                val badgeCount = uiState.badgeCount
                if (badgeCount > 0) {
                    Badge {
                        Text(
                            text = badgeCount.toString(),
                        )
                    }
                }
            }
        ) {
            val image = AnimatedImageVector
                .animatedVectorResource(id = R.drawable.anim_updates_enter)
            Icon(
                painter = rememberAnimatedVectorPainter(
                    animatedImageVector = image,
                    atEnd = name == tabName
                ),
                contentDescription = ""
            )
        }
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Composable
    override fun Content(paddingValues: PaddingValues) {
        val uiState = viewModel.uiState.collectAsState().value
        val context = LocalContext.current
        val snackbarHostState = LocalComposition.current.snackbarHostState
        val scope = rememberCoroutineScope()

        val updateBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent?) {
                when(intent?.action) {
                    COUNTER_UPDATE_ACTION_START -> {
                        viewModel.handleUpdateBadge(intent.getIntExtra(COUNTER_VALUE_EXTRA, 0))
                    }
                    COUNTER_UPDATE_ACTION_STOP -> {
                        UpdateService.stopService(context)
                    }
                }
            }
        }

        DisposableEffect(Unit) {
            context.registerReceiver(updateBroadcastReceiver, IntentFilter(COUNTER_UPDATE_ACTION_START))
            context.registerReceiver(updateBroadcastReceiver, IntentFilter(COUNTER_UPDATE_ACTION_STOP))
            onDispose {
                context.unregisterReceiver(updateBroadcastReceiver)
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "ASD ${uiState.badgeCount}",
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = {
                viewModel.handleUpdate(context) {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            "Update is already in progress"
                        )
                    }
                }
            }) {
                Text(text = "New Update")
            }
        }
    }

}