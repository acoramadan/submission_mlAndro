package com.dicoding.asclepius.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class InformationResponses (
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("totalResults")
    val result: Int,

    @field:SerializedName("articles")
    val information: List<Information>
)

@Parcelize
data class Information (
    @field:SerializedName("source")
    val source: Source?,

    @field:SerializedName("author")
    val author: String?,

    @field:SerializedName("title")
    val title: String?,

    @field:SerializedName("description")
    val description: String?,

    @field:SerializedName("url")
    val url: String?,
    @field:SerializedName("urlToImage")
    val image: String?,

    @field:SerializedName("publishedAt")
    val publish: String?,

    @field:SerializedName("content")
    val content: String?
): Parcelable

@Parcelize
data class Source (
    @field:SerializedName("id")
    val id: String?,
    @field:SerializedName("name")
    val name: String?
): Parcelable