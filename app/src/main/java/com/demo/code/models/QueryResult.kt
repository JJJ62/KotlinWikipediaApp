package com.demo.code.models

import com.google.gson.annotations.SerializedName

data class QueryResult (
    @SerializedName("search")
    val search: List<Article>,
    @SerializedName("pages")
    val pages: List<Article>
)