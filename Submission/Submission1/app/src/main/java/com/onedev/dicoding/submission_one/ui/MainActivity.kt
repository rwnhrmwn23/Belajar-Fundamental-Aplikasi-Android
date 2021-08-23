package com.onedev.dicoding.submission_one.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.onedev.dicoding.submission_one.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}