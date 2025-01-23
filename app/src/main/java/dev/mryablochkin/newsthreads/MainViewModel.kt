package dev.mryablochkin.newsthreads

import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mryablochkin.newsthreads.data.local.repository.UserSettingsRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    userSettingsRepository: UserSettingsRepository
) : BaseViewModel(userSettingsRepository)