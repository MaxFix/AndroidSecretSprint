package com.example.androidsecretsprint.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.androidsecretsprint.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val categoriesBtn: Button = findViewById(R.id.btnCategories)
        val favoritesBtn: Button = findViewById(R.id.btnFavorites)

        categoriesBtn.setOnClickListener {
            findNavController(R.id.mainContainer).navigate(R.id.global_action_to_categoriesListFragment)
        }

        favoritesBtn.setOnClickListener {
            findNavController(R.id.mainContainer).navigate(R.id.global_action_to_favoritesFragment)
        }
    }
}