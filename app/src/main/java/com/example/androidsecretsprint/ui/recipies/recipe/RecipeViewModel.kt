package com.example.androidsecretsprint.ui.recipies.recipe

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidsecretsprint.model.Recipe

data class RecipeUiState(
    val recipe: Recipe? = null,
    val portionsCount: Int = 1,
    var isFavorite: Boolean = false,
    val recipeDrawable: Drawable? = null,
)

class RecipeViewModel() : ViewModel() {
    private val _recipeState = MutableLiveData<RecipeUiState>()
    val recipeState: LiveData<RecipeUiState> = _recipeState

    init {
        _recipeState.value = RecipeUiState(isFavorite = true)
        Log.i("!!!", _recipeState.value.toString())
    }
}