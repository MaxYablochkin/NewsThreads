package dev.mryablochkin.newsthreads.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dev.mryablochkin.newsthreads.presentation.screens.news.newsScreen
import dev.mryablochkin.newsthreads.presentation.screens.settings.settingsScreen

@Composable
internal fun NewsThreadsNavHost(
    modifier: Modifier = Modifier,
    startDestination: String = ScreenRoutes.NewsScreen.route,
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        newsScreen(onSettingsClick = { navController.navigate(ScreenRoutes.SettingsScreen.route) })
        settingsScreen(onBackClick = navController::popBackStack)
    }
}