package dev.mryablochkin.newsthreads.presentation.navigation

sealed class ScreenRoutes(val route: String) {
    data object NewsScreen : ScreenRoutes("NewsScreen")
    data object SettingsScreen : ScreenRoutes("SettingsScreen")

    companion object {
        const val ID = "id"
    }
}