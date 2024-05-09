package com.example.serviceui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.serviceui.databinding.ActivityServiceBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}