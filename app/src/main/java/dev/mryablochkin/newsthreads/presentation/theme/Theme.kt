@file:OptIn(ExperimentalMaterial3Api::class)

package dev.mryablochkin.newsthreads.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

private val AmoledDarkColorScheme = DarkColorScheme.copy(surface = Amoled, background = Amoled)

@Composable
fun NewsThreadsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    // Amoled dark theme
    amoledColor: Boolean = false,
    // Padding theme
    paddings: Padding = MaterialTheme.paddings,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        /**
         * Switch dynamic light and dark themes
         * */
        dynamicColor && !amoledColor && DynamicColorsAvailable -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        /**
         * Switching AMOLED themes, standard and dynamic dark themes,
         * modified to suit the specifics of AMOLED
         * */
        darkTheme && amoledColor -> {
            if (dynamicColor && DynamicColorsAvailable) {
                val context = LocalContext.current
                dynamicDarkColorScheme(context).copy(surface = Amoled, background = Amoled)
            } else AmoledDarkColorScheme
        }

        /**
         * If AMOLED and light theme are enabled at the same time,
         * then only the dynamic color switch will be available,
         * which can be toggled, but AMOLED will not be changed.
         * */
        !darkTheme && amoledColor -> {
            if (dynamicColor && DynamicColorsAvailable) {
                val context = LocalContext.current
                dynamicLightColorScheme(context)
            } else LightColorScheme
        }

        /**
         * Included standard dark theme
         * */
        darkTheme -> DarkColorScheme

        /**
         * Included standard light theme
         * */
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
    ) {
        CompositionLocalProvider(
            LocalPadding provides paddings,
            /*LocalRippleConfiguration provides DefaultRippleConfiguration,*/
            content = content
        )
    }
}