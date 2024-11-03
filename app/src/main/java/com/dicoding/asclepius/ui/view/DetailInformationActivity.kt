package com.dicoding.asclepius.ui.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.asclepius.data.remote.response.Information
import com.dicoding.asclepius.databinding.ActivityDetailInformationBinding

class DetailInformationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailInformationBinding
    private lateinit var information : Information

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailInformationBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        information = intent.getParcelableExtra("EXTRA_TITLE") ?: return
        Log.e("gacor","$information")
        displayInformation(information)

        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnBaca.setOnClickListener {
            val link: String = information.url
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        }

    }

    private fun displayInformation(information: Information) {
        binding.name.text = information.title
        binding.publish.text = information.publish
        binding.ownerName.text = information.author
        binding.content.text = information.content
        binding.description.text = information.description
        Glide.with(this)
            .load(information.image)
            .into(binding.image)
    }
}