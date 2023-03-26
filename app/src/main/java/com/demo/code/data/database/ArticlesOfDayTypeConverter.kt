package com.demo.code.data.database

import androidx.room.TypeConverter
import com.demo.code.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ArticlesOfDayTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun articleOfDayToString(articleOfDayResult: ArticleOfDayResult): String {
        return gson.toJson(articleOfDayResult)
    }

    @TypeConverter
    fun stringToArticleOfDay(data: String): ArticleOfDayResult {
        val listType = object : TypeToken<ArticleOfDayResult>() {}.type
        return gson.fromJson(data, listType)
    }

}