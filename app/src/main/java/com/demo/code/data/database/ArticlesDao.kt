package com.demo.code.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articlesEntity: ArticlesEntity)

    @Query("SELECT * FROM articles_table ORDER BY id ASC")
    fun readArticles(): Flow<List<ArticlesEntity>>
}