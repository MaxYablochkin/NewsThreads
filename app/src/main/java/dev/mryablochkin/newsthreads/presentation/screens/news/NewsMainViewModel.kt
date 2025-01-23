package dev.mryablochkin.newsthreads.presentation.screens.news

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mryablochkin.newsthreads.BaseViewModel
import dev.mryablochkin.newsthreads.data.local.repository.UserSettingsRepository
import dev.mryablochkin.newsthreads.domain.model.Article
import dev.mryablochkin.newsthreads.domain.usecases.NewsArticleUseCase
import dev.mryablochkin.newsthreads.utils.RequestResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NewsMainViewModel @Inject constructor(
    val newsArticleUseCase: NewsArticleUseCase,
    userSettingsRepository: UserSettingsRepository
) : BaseViewModel(userSettingsRepository) {

    private val _newsUiState = MutableStateFlow(NewsUiState())
    val newsUiState = _newsUiState.asStateFlow()

    init {
        getNewsArticle()
    }

    fun refresh() {
        getNewsArticle()
    }

    private fun getNewsArticle() = newsArticleUseCase().onEach {
        when (it) {
            is RequestResult.Success -> _newsUiState.update { newsUiState ->
                newsUiState.copy(data = it.data, isLoading = false)
            }

            is RequestResult.Loading -> _newsUiState.update { newsUiState ->
                delay(1500)
                newsUiState.copy(isLoading = true)
            }

            is RequestResult.Error -> _newsUiState.update { newsUiState ->
                newsUiState.copy(error = it.message.toString(), isLoading = false)
            }
        }
    }.launchIn(viewModelScope)
}

data class NewsUiState(
    val data: List<Article>? = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)