package com.demo.code.models

import com.google.gson.annotations.SerializedName

data class ArticleResult(
    @SerializedName("batchcomplete")
    val batchcomplete: String,
    @SerializedName("query")
    val query: QueryResult,
)