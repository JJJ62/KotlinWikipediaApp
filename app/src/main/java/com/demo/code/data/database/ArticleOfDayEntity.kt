package com.demo.code.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.demo.code.models.*
import com.demo.code.util.Constants.Companion.ARTICLE_OF_DAY_TABLE

@Entity(tableName = ARTICLE_OF_DAY_TABLE)
class ArticleOfDayEntity(
    var articleOfDayResult: ArticleOfDayResult
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}