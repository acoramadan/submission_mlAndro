package com.dicoding.asclepius.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.asclepius.helper.ImageClassifierHelper
import org.tensorflow.lite.task.gms.vision.classifier.Classifications

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _imageUri = MutableLiveData<Uri>()
    val imageUri: LiveData<Uri> get() = _imageUri

    private val _classificationResult = MutableLiveData<String>()
    val classificationResult: LiveData<String> get() = _classificationResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val imageClassifierHelper = ImageClassifierHelper(
        threshold = 0.1f,
        maxResult = 3,
        modelName = "cancer_classification.tflite",
        context = application,
        classifierListener = object : ImageClassifierHelper.ClassifierListener {
            override fun onError(error: String) {
                _errorMessage.postValue("Error: $error")
                _isLoading.postValue(false)
            }

            override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                _isLoading.postValue(false)
                _classificationResult.postValue(formatClassificationResults(results))
            }
        }
    )

    fun setImageUri(uri: Uri) {
        _imageUri.value = uri
    }

    fun classifyImage(uri: Uri) {
        _isLoading.value = true
        imageClassifierHelper.classify(uri)
    }

    private fun formatClassificationResults(results: List<Classifications>?): String {
        val topCategory = results?.firstOrNull()?.categories
            ?.maxByOrNull { it.score }

        return topCategory?.let {
            "${it.label}: ${(it.score * 100).toInt()}%"
        } ?: "No results found."
    }
}
