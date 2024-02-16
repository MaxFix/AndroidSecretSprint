package com.example.androidsecretsprint.ui.recipies.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidsecretsprint.ui.recipies.favorites.PreferencesRepository

class RecipeViewModelFactory(private val repository: PreferencesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            return RecipeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}