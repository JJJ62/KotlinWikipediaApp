package com.demo.code.models

import com.google.gson.annotations.SerializedName

data class ArticleOfDay(
    @SerializedName("type")
    val type: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("extract")
    val extract: String?,
)
