package com.example.androidsecretsprint

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidsecretsprint.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}