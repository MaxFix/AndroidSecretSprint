package com.example.androidsecretsprint

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.androidsecretsprint.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val categoriesBtn: Button = binding.btnCategories
        val favoritesBtn: Button = binding.btnFavorites

        categoriesBtn.setOnClickListener {
            supportFragmentManager.commit {
                replace(R.id.mainContainer, CategoriesListFragment())
            }
        }

        favoritesBtn.setOnClickListener {
            supportFragmentManager.commit {
                replace(R.id.mainContainer, FavoritesFragment())
            }
        }
    }
}