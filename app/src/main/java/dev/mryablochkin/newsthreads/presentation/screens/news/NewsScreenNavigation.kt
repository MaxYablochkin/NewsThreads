package dev.mryablochkin.newsthreads.presentation.screens.news

import androidx.navigation.NavGraphBuilder
import dev.mryablochkin.newsthreads.presentation.navigation.ScreenRoutes
import dev.mryablochkin.newsthreads.presentation.navigation.forwardAndBackwardComposable

internal fun NavGraphBuilder.newsScreen(onSettingsClick: () -> Unit) {
    forwardAndBackwardComposable(route = ScreenRoutes.NewsScreen.route) {
        NewsRoute(onSettingsClick = onSettingsClick)
    }
}