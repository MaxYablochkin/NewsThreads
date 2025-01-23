package dev.mryablochkin.newsthreads.utils

import dev.mryablochkin.newsthreads.data.remote.model.ArticleDto
import dev.mryablochkin.newsthreads.domain.model.Article
import java.util.UUID

internal fun List<ArticleDto>.toArticles(): List<Article> {
    return map {
        Article(
            id = UUID.randomUUID().toString(),
            title = it.title ?: "",
            urlToImage = it.urlToImage ?: "",
            source = it.source,
            publishedAt = it.publishedAt,
        )
    }
}