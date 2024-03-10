package com.example.androidsecretsprint.ui.recipies.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidsecretsprint.data.STUB
import com.example.androidsecretsprint.model.Recipe

data class FavoritesUiState(
    val dataSet: List<Recipe> = emptyList(),
)

class FavoritesViewModel(private val repository: PreferencesRepository) : ViewModel() {
    private val _favoritesState = MutableLiveData<FavoritesUiState>()
    val favoritesState: LiveData<FavoritesUiState> = _favoritesState

    fun loadFavorites() {
        val favorites = repository.getFavorites()
        val favoriteRecipeIds = favorites.mapNotNull { idString ->
            idString.toIntOrNull()
        }.toSet()
        val favoriteRecipes = STUB.getRecipesByIds(favoriteRecipeIds)
        _favoritesState.value = FavoritesUiState().copy(
            dataSet = favoriteRecipes
        )
    }
}