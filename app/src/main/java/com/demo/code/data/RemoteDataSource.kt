package com.demo.code.data

import android.util.Log
import com.demo.code.data.network.ArticlesApi
import com.demo.code.models.ArticleOfDayResult
import com.demo.code.models.QueryResult
import okhttp3.ResponseBody
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class RemoteDataSource  @Inject constructor(
    private val articlesApi: ArticlesApi
) {

    suspend fun getArticles(searchQuery:String): Response<QueryResult> {
        val queries: MutableMap<String, String> = HashMap()
        queries.put("srsearch",searchQuery)
        queries.put("action","query")
        queries.put("list","search")
        queries.put("format","json")

//        queries.put("format","json")
//        queries.put("action","query")
//        queries.put("generator","search")
//        queries.put("gsrnamespace","0")
//        queries.put("gsrsearch",searchQuery)
//        queries.put("gsrlimit","10")
//        queries.put("prop","pageimages|extracts")
//        queries.put("pilimit","max")
//        queries.put("exintro","")
//        queries.put("explaintext","")
//        queries.put("exsentences","50")
//        queries.put("exlimit","max")


        val articleResult = articlesApi.getArticles(queries)
        return articleResult.body()?.query?.let {
            return Response.success(it)
        } ?: Response.error(400, ResponseBody.create(null, "Error"))

    }

    suspend fun getArticleByTitle(searchQuery:String): Response<QueryResult> {
        val queries: MutableMap<String, String> = HashMap()
        queries.put("action","query")
        queries.put("prop","extracts")
        queries.put("exsentences","10")
        queries.put("exlimit","1")
        queries.put("titles",searchQuery)
        queries.put("explaintext","1")
        queries.put("formatversion","2")
        queries.put("format","json")

//        queries.put("format","json")
//        queries.put("action","query")
//        queries.put("generator","search")
//        queries.put("gsrnamespace","0")
//        queries.put("gsrsearch",searchQuery)
//        queries.put("gsrlimit","10")
//        queries.put("prop","pageimages|extracts")
//        queries.put("pilimit","max")
//        queries.put("exintro","")
//        queries.put("explaintext","")
//        queries.put("exsentences","50")
//        queries.put("exlimit","max")


        val articleResult = articlesApi.getArticles(queries)
        return articleResult.body()?.query?.let {
            return Response.success(it)
        } ?: Response.error(400, ResponseBody.create(null, "Error"))

    }

    suspend fun getArticleOfTheDay():Response<ArticleOfDayResult>{
        //get date today in yyyy/mm/dd format
        val today = SimpleDateFormat("yyyy/MM/dd").format(Date()).toString()

        //get year from today
        val year = today.split("/")[0]
        //get month from today
        val month = today.split("/")[1]
        //get day from today
        val day = today.split("/")[2]
        val articleOfDayResult = articlesApi.getArticleOfTheDay(year,month,day)
        return articleOfDayResult.body()?.let {
            return Response.success(it)
        } ?: Response.error(400, ResponseBody.create(null, "Error"))
    }

}
