package com.dicoding.asclepius.data.local.entity

import android.net.Uri
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
@Entity(tableName = "history")
@Parcelize
data class HistoryEntity(
    @PrimaryKey
    var id: Int? = null,

    @ColumnInfo(name = "image")
    var image: String? = null,

    @ColumnInfo(name = "result")
    var result: String? = null
):Parcelable