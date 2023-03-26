package com.demo.code.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demo.code.models.FavoriteArticles
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteArticlesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteArticles(favoriteArticlesEntity: FavoriteArticlesEntity)

    @Query("SELECT * FROM favorite_articles_table ORDER BY id ASC")
    fun readFavoriteArticles(): Flow<List<FavoriteArticlesEntity>>
}