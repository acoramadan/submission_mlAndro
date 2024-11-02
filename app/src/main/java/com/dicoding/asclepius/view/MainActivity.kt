package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.viewmodel.MainViewModel
import com.yalantis.ucrop.UCrop
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryButton.setOnClickListener {
            startGallery()
        }

        binding.analyzeButton.setOnClickListener {
            analyzeImage()
        }

        observeViewModel()
    }

    private val launchGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            startCrop(uri)  // Memulai uCrop setelah gambar dipilih
        } else {
            Log.e("MainActivity", "Error Memunculkan galeri")
        }
    }

    private fun startGallery() {
        launchGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun startCrop(uri: Uri) {
        val destinationUri = Uri.fromFile(File(cacheDir, "cropped_image.jpg"))
        val options = UCrop.Options()
        options.setFreeStyleCropEnabled(true)  // Mengaktifkan crop secara bebas
        options.setCompressionQuality(100)  // Set kualitas gambar setelah crop

        UCrop.of(uri, destinationUri)
            .withAspectRatio(1f, 1f)  // Anda bisa menyesuaikan rasio ini
            .withMaxResultSize(224, 224)  // Sesuaikan ukuran maksimal
            .withOptions(options)
            .start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            val resultUri = UCrop.getOutput(data!!)
            currentImageUri = resultUri
            showImage()  // Memastikan tampilan gambar diperbarui dengan hasil crop
            viewModel.setImageUri(resultUri!!)  // Set URI pada ViewModel untuk klasifikasi
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
            showToast("Crop error: ${cropError?.message}")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)  // Update ImageView dengan URI terbaru
        }
    }

    private fun analyzeImage() {
        currentImageUri?.let { uri ->
            binding.progressIndicator.visibility = View.VISIBLE
            viewModel.classifyImage(uri)
        } ?: showToast("Pilih gambar terlebih dahulu.")
    }

    private fun moveToResult(resultText: String) {
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("classificationResult", resultText)
            putExtra("imageUri", currentImageUri.toString())
        }
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun observeViewModel() {
        viewModel.classificationResult.observe(this, Observer { result ->
            binding.progressIndicator.visibility = View.GONE
            result?.let {
                moveToResult(it)  // Pindah ke ResultActivity dengan hasil klasifikasi
            } ?: showToast("Gagal menganalisis gambar.")
        })

        viewModel.errorMessage.observe(this, Observer { error ->
            binding.progressIndicator.visibility = View.GONE
            showToast(error)
        })

        viewModel.isLoading.observe(this, Observer { isLoading ->
            binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
        })
    }
}
