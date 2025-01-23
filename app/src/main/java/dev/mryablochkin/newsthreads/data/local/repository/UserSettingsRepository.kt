package dev.mryablochkin.newsthreads.data.local.repository

import dev.mryablochkin.newsthreads.data.local.preferences.ThemeSettings
import kotlinx.coroutines.flow.StateFlow

interface UserSettingsRepository {
    var theme: ThemeSettings
    val themeStateFlow: StateFlow<ThemeSettings>
    var dynamicColor: Boolean
    val dynamicColorStateFlow: StateFlow<Boolean>
    var amoledColor: Boolean
    val amoledColorStateFlow: StateFlow<Boolean>

    suspend fun updateDynamicColor()
    suspend fun updateAmoledColor()
    suspend fun updateTheme(themeSettings: ThemeSettings)
}