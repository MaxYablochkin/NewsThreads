package dev.mryablochkin.newsthreads

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mryablochkin.newsthreads.data.local.preferences.ThemeSettings
import dev.mryablochkin.newsthreads.data.local.repository.UserSettingsRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(
    userSettingsRepository: UserSettingsRepository
) : ViewModel() {
    val theme: StateFlow<ThemeSettings> = userSettingsRepository.themeStateFlow
    val dynamicColor: StateFlow<Boolean> = userSettingsRepository.dynamicColorStateFlow
    val amoledColor: StateFlow<Boolean> = userSettingsRepository.amoledColorStateFlow
}