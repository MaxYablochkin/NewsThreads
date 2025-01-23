package dev.mryablochkin.newsthreads.data.remote.api

import dev.mryablochkin.newsthreads.data.remote.model.NewsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "ua",
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsDto>

    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = "727ef221e6134b31905d32db488fd0de"
    }
}