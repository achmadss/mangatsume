package com.achmadss.mangatsume.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

object MaterialColors {
    val Custom10 = Color(red = 16, green = 17, blue = 22)
    val Black = Color(red = 0, green = 0, blue = 0)
    val Error0 = Color(red = 0, green = 0, blue = 0)
    val Error10 = Color(red = 65, green = 14, blue = 11)
    val Error100 = Color(red = 255, green = 255, blue = 255)
    val Error20 = Color(red = 96, green = 20, blue = 16)
    val Error30 = Color(red = 140, green = 29, blue = 24)
    val Error40 = Color(red = 179, green = 38, blue = 30)
    val Error50 = Color(red = 220, green = 54, blue = 46)
    val Error60 = Color(red = 228, green = 105, blue = 98)
    val Error70 = Color(red = 236, green = 146, blue = 142)
    val Error80 = Color(red = 242, green = 184, blue = 181)
    val Error90 = Color(red = 249, green = 222, blue = 220)
    val Error95 = Color(red = 252, green = 238, blue = 238)
    val Error99 = Color(red = 255, green = 251, blue = 249)
    val Neutral0 = Color(red = 0, green = 0, blue = 0)
    val Neutral10 = Color(red = 28, green = 27, blue = 31)
    val Neutral100 = Color(red = 255, green = 255, blue = 255)
    val Neutral20 = Color(red = 49, green = 48, blue = 51)
    val Neutral30 = Color(red = 72, green = 70, blue = 73)
    val Neutral40 = Color(red = 96, green = 93, blue = 98)
    val Neutral50 = Color(red = 120, green = 117, blue = 121)
    val Neutral60 = Color(red = 147, green = 144, blue = 148)
    val Neutral70 = Color(red = 174, green = 170, blue = 174)
    val Neutral80 = Color(red = 201, green = 197, blue = 202)
    val Neutral90 = Color(red = 230, green = 225, blue = 229)
    val Neutral95 = Color(red = 244, green = 239, blue = 244)
    val Neutral99 = Color(red = 255, green = 251, blue = 254)
    val NeutralVariant0 = Color(red = 0, green = 0, blue = 0)
    val NeutralVariant10 = Color(red = 29, green = 26, blue = 34)
    val NeutralVariant100 = Color(red = 255, green = 255, blue = 255)
    val NeutralVariant20 = Color(red = 50, green = 47, blue = 55)
    val NeutralVariant30 = Color(red = 73, green = 69, blue = 79)
    val NeutralVariant40 = Color(red = 96, green = 93, blue = 102)
    val NeutralVariant50 = Color(red = 121, green = 116, blue = 126)
    val NeutralVariant60 = Color(red = 147, green = 143, blue = 153)
    val NeutralVariant70 = Color(red = 174, green = 169, blue = 180)
    val NeutralVariant80 = Color(red = 202, green = 196, blue = 208)
    val NeutralVariant90 = Color(red = 231, green = 224, blue = 236)
    val NeutralVariant95 = Color(red = 245, green = 238, blue = 250)
    val NeutralVariant99 = Color(red = 255, green = 251, blue = 254)
    val Primary0 = Color(red = 0, green = 0, blue = 0)
    val Primary10 = Color(red = 33, green = 0, blue = 93)
    val Primary100 = Color(red = 255, green = 255, blue = 255)
    val Primary20 = Color(red = 56, green = 30, blue = 114)
    val Primary30 = Color(red = 79, green = 55, blue = 139)
    val Primary40 = Color(red = 103, green = 80, blue = 164)
    val Primary50 = Color(red = 127, green = 103, blue = 190)
    val Primary60 = Color(red = 154, green = 130, blue = 219)
    val Primary70 = Color(red = 182, green = 157, blue = 248)
    val Primary80 = Color(red = 208, green = 188, blue = 255)
    val Primary90 = Color(red = 234, green = 221, blue = 255)
    val Primary95 = Color(red = 246, green = 237, blue = 255)
    val Primary99 = Color(red = 255, green = 251, blue = 254)
    val Secondary0 = Color(red = 0, green = 0, blue = 0)
    val Secondary10 = Color(red = 29, green = 25, blue = 43)
    val Secondary100 = Color(red = 255, green = 255, blue = 255)
    val Secondary20 = Color(red = 51, green = 45, blue = 65)
    val Secondary30 = Color(red = 74, green = 68, blue = 88)
    val Secondary40 = Color(red = 98, green = 91, blue = 113)
    val Secondary50 = Color(red = 122, green = 114, blue = 137)
    val Secondary60 = Color(red = 149, green = 141, blue = 165)
    val Secondary70 = Color(red = 176, green = 167, blue = 192)
    val Secondary80 = Color(red = 204, green = 194, blue = 220)
    val Secondary90 = Color(red = 232, green = 222, blue = 248)
    val Secondary95 = Color(red = 246, green = 237, blue = 255)
    val Secondary99 = Color(red = 255, green = 251, blue = 254)
    val Tertiary0 = Color(red = 0, green = 0, blue = 0)
    val Tertiary10 = Color(red = 49, green = 17, blue = 29)
    val Tertiary100 = Color(red = 255, green = 255, blue = 255)
    val Tertiary20 = Color(red = 73, green = 37, blue = 50)
    val Tertiary30 = Color(red = 99, green = 59, blue = 72)
    val Tertiary40 = Color(red = 125, green = 82, blue = 96)
    val Tertiary50 = Color(red = 152, green = 105, blue = 119)
    val Tertiary60 = Color(red = 181, green = 131, blue = 146)
    val Tertiary70 = Color(red = 210, green = 157, blue = 172)
    val Tertiary80 = Color(red = 239, green = 184, blue = 200)
    val Tertiary90 = Color(red = 255, green = 216, blue = 228)
    val Tertiary95 = Color(red = 255, green = 236, blue = 241)
    val Tertiary99 = Color(red = 255, green = 251, blue = 250)
    val White = Color(red = 255, green = 255, blue = 255)
}

private val DarkColorScheme = darkColorScheme(
    primary = MaterialColors.Primary80,
    onPrimary = MaterialColors.Primary20,
    primaryContainer = MaterialColors.Primary30,
    onPrimaryContainer = MaterialColors.Primary90,
    inversePrimary = MaterialColors.Primary40,
    secondary = MaterialColors.Secondary80,
    onSecondary = MaterialColors.Secondary20,
    secondaryContainer = MaterialColors.Secondary30,
    onSecondaryContainer = MaterialColors.Secondary90,
    tertiary = MaterialColors.Tertiary80,
    onTertiary = MaterialColors.Tertiary20,
    tertiaryContainer = MaterialColors.Tertiary30,
    onTertiaryContainer = MaterialColors.Tertiary90,
    background = MaterialColors.Neutral10,
    onBackground = MaterialColors.Neutral90,
    surface = MaterialColors.Neutral10,
    onSurface = MaterialColors.Neutral90,
    surfaceVariant = MaterialColors.NeutralVariant30,
    onSurfaceVariant = MaterialColors.NeutralVariant80,
    inverseSurface = MaterialColors.Neutral90,
    inverseOnSurface = MaterialColors.Neutral20,
    outline = MaterialColors.NeutralVariant60,
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun MangatsumeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkColorScheme().copy(
            background = MaterialColors.Neutral0,
            surface = MaterialColors.Custom10
        )
        else -> lightColorScheme()
    }

    StatusBarColor(color = Color.Transparent)

    val navigationBarColor = if (darkTheme) MaterialColors.Custom10 else MaterialColors.White
    NavigationBarColor(color = navigationBarColor)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun NavigationBarColor(color: Color) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.navigationBarColor = color.toArgb()
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
    }
}

@Composable
fun StatusBarColor(color: Color) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = color.toArgb()
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
    }
}