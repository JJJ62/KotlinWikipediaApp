package com.demo.code.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.demo.code.models.FavoriteArticles
import com.demo.code.util.Constants

@Entity(tableName = Constants.FAVORITE_ARTICLES_TABLE)
class FavoriteArticlesEntity(
    val favoriteArticles: FavoriteArticles
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}