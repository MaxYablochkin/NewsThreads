package dev.mryablochkin.newsthreads.data.remote.model

data class NewsDto(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleDto>
)