package com.dicoding.asclepius.helper

import org.tensorflow.lite.task.vision.classifier.Classifications

interface ClassifierListener {
    fun onError(error: String)
    fun onResults(
        results: List<Classifications>?,
        inferenceTime: Long
    )
}