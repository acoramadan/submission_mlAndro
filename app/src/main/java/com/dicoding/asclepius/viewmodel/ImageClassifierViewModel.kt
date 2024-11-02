package com.dicoding.asclepius.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.helper.ImageClassifierHelper
import org.tensorflow.lite.task.gms.vision.classifier.Classifications

class ImageClassifierViewModel(context: Context) : ViewModel(), ImageClassifierHelper.ClassifierListener {

    private val _classificationResults = MutableLiveData<List<Classifications>>()
    val classificationResults: LiveData<List<Classifications>> = _classificationResults

    private val _inferenceTime = MutableLiveData<Long>()
    val inferenceTime: LiveData<Long> = _inferenceTime

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val imageClassifierHelper = ImageClassifierHelper(
        context = context,
        classifierListener = this
    )

    fun classifyImage(imageUri: Uri) {
        imageClassifierHelper.classify(imageUri)
    }

    override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
        _classificationResults.postValue(results)
        _inferenceTime.postValue(inferenceTime)
    }

    override fun onError(error: String) {
        _errorMessage.postValue(error)
    }
}
