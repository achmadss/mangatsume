package com.achmadss.mangatsume

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.achmadss.mangatsume.ui.root.AppRoot
import com.achmadss.mangatsume.ui.theme.MangatsumeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MangatsumeTheme {
                enableEdgeToEdge()
                AppRoot()
            }
        }
    }
}
