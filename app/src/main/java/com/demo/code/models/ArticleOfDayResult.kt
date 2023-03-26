package com.demo.code.models

import com.google.gson.annotations.SerializedName

data class ArticleOfDayResult (
    @SerializedName("tfa")
    val articleOfDay: ArticleOfDay?
)