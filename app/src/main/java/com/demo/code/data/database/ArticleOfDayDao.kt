package com.demo.code.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demo.code.models.ArticleOfDay
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleOfDayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticleOfDay(articleOfDayEntity: ArticleOfDayEntity)

    @Query("SELECT * FROM article_of_day_table ORDER BY id ASC")
    fun readArticles(): Flow<ArticleOfDayEntity>
}