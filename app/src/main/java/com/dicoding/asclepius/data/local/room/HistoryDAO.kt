package com.dicoding.asclepius.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.asclepius.data.local.entity.HistoryEntity

@Dao
interface HistoryDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(history: HistoryEntity)

    @Delete
    suspend fun delete(history: HistoryEntity)

    @Query("SELECT * FROM history")
    fun getAllHistory(): LiveData<List<HistoryEntity>>
}