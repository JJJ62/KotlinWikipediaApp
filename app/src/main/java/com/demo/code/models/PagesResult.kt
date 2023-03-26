package com.demo.code.models

import com.google.gson.annotations.SerializedName

data class PagesResult(
    @SerializedName("pages")
    val articles: Map<String,Article>
)
