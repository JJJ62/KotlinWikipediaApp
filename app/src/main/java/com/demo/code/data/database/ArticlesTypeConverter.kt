package com.demo.code.data.database

import androidx.room.TypeConverter
import com.demo.code.models.QueryResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ArticlesTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun articleToString(article: QueryResult): String {
        return gson.toJson(article)
    }

    @TypeConverter
    fun stringToArticle(data: String): QueryResult {
        val listType = object : TypeToken<QueryResult>() {}.type
        return gson.fromJson(data, listType)
    }

}