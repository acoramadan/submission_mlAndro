package com.dicoding.asclepius.data.remote.retrofit

import com.dicoding.asclepius.data.remote.response.InformationResponses
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getCancerHealthNews(
        @Query("q") query: String = "cancer",
        @Query("language") language: String = "en",
        @Query("category") category: String = "health",
        @Query("apiKey") apiKey: String
    ): Response<InformationResponses>
}