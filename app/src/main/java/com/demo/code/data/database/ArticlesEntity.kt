package com.demo.code.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.demo.code.models.QueryResult
import com.demo.code.util.Constants.Companion.ARTICLES_TABLE

@Entity(tableName = ARTICLES_TABLE)
class ArticlesEntity(
    var queryResult: QueryResult
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}