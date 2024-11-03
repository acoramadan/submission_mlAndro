package com.dicoding.asclepius.ui.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.entity.HistoryEntity
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.ui.viewmodel.HistoryViewModel

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
        binding.fab.setOnClickListener{
            saveToDatabase(imageUriString,classificationResult)
        }
    }
    private val historyViewModel: HistoryViewModel by viewModels {
        HistoryViewModelFactory.getInstance(application)
    }
    private fun saveToDatabase(imageUriString: String?, result: String?) {
        if (imageUriString != null && result != null) {
            val historyEntity = HistoryEntity(
                image = imageUriString,
                result = result
            )
            historyViewModel.insert(historyEntity)

            Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Gagal menyimpan data, pastikan data valid", Toast.LENGTH_SHORT).show()
        }
    }
}
