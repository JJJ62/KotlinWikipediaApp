package com.demo.code.data.network

import com.demo.code.models.ArticleOfDayResult
import com.demo.code.models.ArticleResult
import com.demo.code.models.PagesResult
import com.demo.code.models.QueryResult
import retrofit2.Response
import retrofit2.http.*

interface ArticlesApi {

    @GET("w/api.php")
    suspend fun getArticles(
        @QueryMap queries: Map<String, String>
    ): Response<ArticleResult>

//    @GET("w/api.php")
//    suspend fun getArticles(
//        @QueryMap queries: Map<String, String>
//    ): Response<PagesResult>



    @GET("https://api.wikimedia.org/feed/v1/wikipedia/en/featured/{year}/{month}/{day}")
    suspend fun getArticleOfTheDay(
        @Path("year") year: String,
        @Path("month") month: String,
        @Path("day") day: String
    ): Response<ArticleOfDayResult>
}