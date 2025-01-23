package dev.mryablochkin.newsthreads.domain.usecases

import dev.mryablochkin.newsthreads.domain.repository.NewsArticleRepository
import dev.mryablochkin.newsthreads.utils.RequestResult
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsArticleUseCase @Inject constructor(
    private val newsArticleRepository: NewsArticleRepository
) {
    operator fun invoke() = flow {
        emit(RequestResult.Loading(""))
        try {
            emit(RequestResult.Success(newsArticleRepository.getNewsArticle()))
        } catch (e: Exception) {
            emit(RequestResult.Error(e.message))
        }
    }
}