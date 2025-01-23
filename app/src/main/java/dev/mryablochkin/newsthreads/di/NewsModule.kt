package dev.mryablochkin.newsthreads.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mryablochkin.newsthreads.data.remote.api.NewsApiService
import dev.mryablochkin.newsthreads.data.remote.repository.NewsArticleRepositoryImpl
import dev.mryablochkin.newsthreads.domain.repository.NewsArticleRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NewsModule {
    @Provides
    @Singleton
    fun provideNewsArticle(newsApiService: NewsApiService): NewsArticleRepository {
        return NewsArticleRepositoryImpl(newsApiService)
    }
}