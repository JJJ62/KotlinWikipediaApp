package com.demo.code.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [ArticlesEntity::class,ArticleOfDayEntity::class,
        FavoriteArticlesEntity::class, SearchHistoryEntity::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(ArticlesTypeConverter::class,ArticlesOfDayTypeConverter::class,
    FavoriteArticlesTypeConverter::class)
abstract class ArticlesDatabase: RoomDatabase() {

    abstract fun articlesDao(): ArticlesDao
    abstract fun articlesOfDayDao(): ArticleOfDayDao
    abstract fun favoriteArticlesDao(): FavoriteArticlesDao
    abstract fun searchHistoryDao(): SearchHistoryDao

}