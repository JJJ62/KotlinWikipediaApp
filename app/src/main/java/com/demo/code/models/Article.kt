package com.demo.code.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class Article(
    @SerializedName("ns")
    val ns: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("pageid")
    val pageid: Int?,
    @SerializedName("snippet")
    val snippet: String?,
    @SerializedName("extract")
    val extract: String?,
): Parcelable