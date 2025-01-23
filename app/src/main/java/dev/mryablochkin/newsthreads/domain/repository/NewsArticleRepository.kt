package dev.mryablochkin.newsthreads.domain.repository

import dev.mryablochkin.newsthreads.domain.model.Article

interface NewsArticleRepository {
    suspend fun getNewsArticle(): List<Article>
}