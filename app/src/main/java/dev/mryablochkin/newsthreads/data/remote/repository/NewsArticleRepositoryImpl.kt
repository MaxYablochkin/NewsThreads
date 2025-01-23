package dev.mryablochkin.newsthreads.data.remote.repository

import dev.mryablochkin.newsthreads.data.remote.api.NewsApiService
import dev.mryablochkin.newsthreads.domain.model.Article
import dev.mryablochkin.newsthreads.domain.repository.NewsArticleRepository
import dev.mryablochkin.newsthreads.utils.SafeRequest
import dev.mryablochkin.newsthreads.utils.toArticles
import javax.inject.Inject

class NewsArticleRepositoryImpl @Inject constructor(
    private val newsApi: NewsApiService
) : NewsArticleRepository, SafeRequest() {

    override suspend fun getNewsArticle(): List<Article> {
        val response = safeRequest { newsApi.getTopHeadlines() }
        return response.articles.toArticles()
    }
}