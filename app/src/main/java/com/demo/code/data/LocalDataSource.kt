package com.demo.code.data

import com.demo.code.data.database.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val articlesDao: ArticlesDao,
    private val articleOfDayDao: ArticleOfDayDao,
    private val favoriteArticlesDao: FavoriteArticlesDao,
    private val searchHistoryDao: SearchHistoryDao
) {

    fun readDatabase(): Flow<List<ArticlesEntity>> {
        return articlesDao.readArticles()
    }

    suspend fun insertArticles(articlesEntity: ArticlesEntity) {
        articlesDao.insertArticles(articlesEntity)
    }

    fun readArticleOfDay(): Flow<ArticleOfDayEntity> {
        return articleOfDayDao.readArticles()
    }

    suspend fun insertArticleOfDay(articleOfDayEntity: ArticleOfDayEntity) {
        articleOfDayDao.insertArticleOfDay(articleOfDayEntity)
    }

    fun readFavoriteArticles(): Flow<List<FavoriteArticlesEntity>> {
        return favoriteArticlesDao.readFavoriteArticles()
    }

    suspend fun insertFavoriteArticles(favoriteArticlesEntity: FavoriteArticlesEntity) {
        favoriteArticlesDao.insertFavoriteArticles(favoriteArticlesEntity)
    }

    fun readSearchHistory(): Flow<List<SearchHistoryEntity>> {
        return searchHistoryDao.readSearchHistory()
    }

    suspend fun insertSearchHistory(searchHistoryEntity: SearchHistoryEntity) {
        searchHistoryDao.insertSearchHistory(searchHistoryEntity)
    }

}