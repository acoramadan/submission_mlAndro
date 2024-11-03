package com.dicoding.asclepius.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.asclepius.data.local.entity.HistoryEntity

@Database(entities = [HistoryEntity::class], version = 1)
abstract class HistoryRoomDatabase: RoomDatabase() {
    abstract fun historyDao() : HistoryDAO

    companion object{
        @Volatile
        private var INSTANCE: HistoryRoomDatabase? = null
        fun getDatabase(context: Context): HistoryRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HistoryRoomDatabase::class.java, "history_database"
                )
                    .fallbackToDestructiveMigrationFrom()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}