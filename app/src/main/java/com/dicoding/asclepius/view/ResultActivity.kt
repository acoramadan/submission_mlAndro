package com.dicoding.asclepius.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUriString = intent.getStringExtra("imageUri")
        val classificationResult = intent.getStringExtra("classificationResult")

        val imageUri = imageUriString?.let { Uri.parse(it) }
        binding.resultImage.setImageURI(imageUri)

        binding.resultText.text = classificationResult ?: "ERROR TIDAK ADA GAMBAR!"
    }
}
