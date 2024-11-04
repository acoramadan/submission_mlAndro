package com.dicoding.asclepius.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.ui.view.fragments.HistoryFragment
import com.dicoding.asclepius.ui.view.fragments.InformationFragment

class MainActivity : AppCompatActivity() {
   private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(R.id.frame_container,InformationFragment()).commit()

        binding.floatingActionButton.setOnClickListener{
            val intent = Intent(this@MainActivity,DetectionActivity::class.java)
            startActivity(intent)
        }
        binding.bottomNavigation.setOnItemSelectedListener{ item->
            when(item.itemId) {
                R.id.berita -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_container,InformationFragment())
                        .commit()
                    true
                }
                R.id.riwayat -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_container,HistoryFragment())
                        .commit()
                    true
                }
                else ->  false
            }
        }
    }
}
