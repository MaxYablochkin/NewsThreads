package dev.mryablochkin.newsthreads.presentation.screens.settings

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mryablochkin.newsthreads.BaseViewModel
import dev.mryablochkin.newsthreads.data.local.preferences.ThemeSettings
import dev.mryablochkin.newsthreads.data.local.repository.UserSettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userSettingsRepository: UserSettingsRepository,
) : BaseViewModel(userSettingsRepository) {

    private val _settingsUiState = MutableStateFlow(SettingsUiState())
    val settingsUiState = _settingsUiState.asStateFlow()

    fun updateTheme(themeSettings: ThemeSettings) = viewModelScope.launch {
        userSettingsRepository.updateTheme(themeSettings)
    }

    fun updateDynamicColor() = viewModelScope.launch {
        userSettingsRepository.updateDynamicColor()
    }

    fun updateAmoledColor() = viewModelScope.launch {
        userSettingsRepository.updateAmoledColor()
    }

    fun showDialog() = _settingsUiState.update { it.copy(themeDialogVisible = true) }

    fun hideDialog() = _settingsUiState.update { it.copy(themeDialogVisible = false) }
}

data class SettingsUiState(
    val themeDialogVisible: Boolean = false
)