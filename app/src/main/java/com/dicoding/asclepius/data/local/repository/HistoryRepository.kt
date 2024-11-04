package com.dicoding.asclepius.data.local.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.asclepius.data.local.entity.HistoryEntity
import com.dicoding.asclepius.data.local.room.HistoryDAO
import com.dicoding.asclepius.data.local.room.HistoryRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HistoryRepository(application: Application) {
    private val historyDao: HistoryDAO

    init {
        val db = HistoryRoomDatabase.getDatabase(application)
        historyDao = db.historyDao()
    }
    fun getAllHistory(): LiveData<List<HistoryEntity>> = historyDao.getAllHistory()

    suspend fun insert(historyEntity: HistoryEntity) {
        withContext(Dispatchers.IO) {
            historyDao.insert(historyEntity)
        }
    }
}