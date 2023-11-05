package com.example.androidsecretsprint

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.androidsecretsprint.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val categoriesBtn: Button = findViewById(R.id.btnCategories)
        val favoritesBtn: Button = findViewById(R.id.btnFavorites)

        categoriesBtn.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, CategoriesListFragment())
                .commit()
        }

        favoritesBtn.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, FavoritesFragment())
                .commit()
        }
    }
}