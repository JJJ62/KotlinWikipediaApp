package com.demo.code.data.database

import androidx.room.TypeConverter
import com.demo.code.models.ArticleOfDayResult
import com.demo.code.models.FavoriteArticles
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavoriteArticlesTypeConverter {
    var gson = Gson()

    @TypeConverter
    fun favoriteArticleToString(favoriteArticles: FavoriteArticles): String {
        return gson.toJson(favoriteArticles)
    }

    @TypeConverter
    fun stringToFavoriteArticle(data: String): FavoriteArticles {
        val listType = object : TypeToken<FavoriteArticles>() {}.type
        return gson.fromJson(data, listType)
    }
}