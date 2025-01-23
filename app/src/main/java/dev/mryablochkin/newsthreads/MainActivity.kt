package dev.mryablochkin.newsthreads

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import dev.mryablochkin.newsthreads.data.local.preferences.ThemeSettings
import dev.mryablochkin.newsthreads.presentation.navigation.NewsThreadsNavHost
import dev.mryablochkin.newsthreads.presentation.screens.news.NewsMainViewModel
import dev.mryablochkin.newsthreads.presentation.theme.NewsThreadsTheme
import dev.mryablochkin.newsthreads.presentation.theme.Transparent

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<NewsMainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val theme by viewModel.theme.collectAsState()
            val dynamicColor by viewModel.dynamicColor.collectAsState()
            val amoledColor by viewModel.amoledColor.collectAsState()
            val darkTheme = useDarkTheme(theme)

            DisposableEffect(darkTheme) {
                enableEdgeToEdge(
                    SystemBarStyle.auto(Transparent, Transparent) { darkTheme },
                    SystemBarStyle.auto(Transparent, Transparent) { darkTheme },
                )
                onDispose {}
            }

            NewsThreadsTheme(darkTheme, dynamicColor, amoledColor) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NewsThreadsNavHost()
                }
            }
        }
    }
}

@Composable
internal fun useDarkTheme(theme: ThemeSettings): Boolean {
    return when (theme) {
        ThemeSettings.Light -> false
        ThemeSettings.Dark -> true
        ThemeSettings.System -> isSystemInDarkTheme()
    }
}