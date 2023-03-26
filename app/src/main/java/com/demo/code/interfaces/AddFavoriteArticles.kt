package com.demo.code.interfaces

import com.demo.code.data.database.FavoriteArticlesEntity

interface AddFavoriteArticles {
    fun addFavoriteArticles(favoriteArticlesEntity: FavoriteArticlesEntity)
}