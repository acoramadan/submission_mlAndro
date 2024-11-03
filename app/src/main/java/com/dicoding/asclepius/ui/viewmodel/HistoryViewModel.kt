package com.dicoding.asclepius.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.local.entity.HistoryEntity
import com.dicoding.asclepius.data.local.repository.HistoryRepository
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: HistoryRepository) : ViewModel() {

    val allHistory: LiveData<List<HistoryEntity>> = repository.getAllHistory()

    fun insert(historyEntity: HistoryEntity) {
        viewModelScope.launch {
            repository.insert(historyEntity)
        }
    }

    fun delete(historyEntity: HistoryEntity) {
        viewModelScope.launch {
            repository.delete(historyEntity)
        }
    }
}
