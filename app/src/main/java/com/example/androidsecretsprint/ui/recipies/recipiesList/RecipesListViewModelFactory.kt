package com.example.androidsecretsprint.ui.recipies.recipiesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidsecretsprint.ui.recipies.favorites.PreferencesRepository

class RecipesListViewModelFactory(private val repository: PreferencesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipesListViewModel::class.java)) {
            return RecipesListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}