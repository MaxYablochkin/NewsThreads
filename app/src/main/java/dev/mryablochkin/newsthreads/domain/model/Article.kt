package dev.mryablochkin.newsthreads.domain.model

import dev.mryablochkin.newsthreads.data.remote.model.SourceDto

data class Article(
    val id: String,
    val title: String,
    val urlToImage: String,
    val source: SourceDto,
    val publishedAt: String,
)