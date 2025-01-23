@file:RequiresApi(Build.VERSION_CODES.O)

package dev.mryablochkin.newsthreads.presentation.screens.settings

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import dev.mryablochkin.newsthreads.presentation.navigation.ScreenRoutes
import dev.mryablochkin.newsthreads.presentation.navigation.forwardAndBackwardComposable

internal fun NavGraphBuilder.settingsScreen(onBackClick: () -> Unit) {
    forwardAndBackwardComposable(route = ScreenRoutes.SettingsScreen.route) {
        SettingsRoute(onBackClick = onBackClick)
    }
}