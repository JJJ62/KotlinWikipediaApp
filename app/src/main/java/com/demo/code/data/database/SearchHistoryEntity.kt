package com.demo.code.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.demo.code.util.Constants

@Entity(tableName = Constants.SEARCH_HISTORY_TABLE)
class SearchHistoryEntity(
    var searchedString:String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}