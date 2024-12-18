package com.dicoding.asclepius.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.dicoding.asclepius.data.remote.response.InformationResponses
import com.dicoding.asclepius.data.remote.retrofit.ApiConfig

class InformationNewsViewModel : ViewModel() {

    private val _newsResponse = MutableLiveData<InformationResponses>()
    val newsResponse: LiveData<InformationResponses> get() = _newsResponse

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val apiService = ApiConfig.getApiService()

    fun fetchCancerHealthNews(apiKey: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = apiService.getCancerHealthNews(apiKey = apiKey)
                if (response.isSuccessful && response.body() != null) {
                    val articles = response.body()?.information?.filter { article ->
                        article.title!!.isNotBlank() &&
                            article.description!!.isNotBlank()
                                && !article.title.contains("[Removed]", ignoreCase = true)
                                && !article.description.contains("[Removed]", ignoreCase = true)
                    } ?: emptyList()

                    _newsResponse.postValue(response.body()?.copy(information = articles))
                } else {
                    _errorMessage.postValue("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue("Exception: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
