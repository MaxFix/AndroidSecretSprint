package com.example.androidsecretsprint.ui.recipies.recipe

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidsecretsprint.model.Recipe

data class RecipeUiState(
    val favoriteRecipe: Boolean = false,
    val portionsCount: Int = 1,
)

class RecipeViewModel(
    val recipe: Recipe? = null,
    val recipeDrawable: Drawable? = null,
    val favoriteRecipe: Boolean = false,
    val portionsCount: Int? = null,
) : ViewModel() {

    private val _recipeState: MutableLiveData<RecipeUiState> = MutableLiveData(RecipeUiState())
    val recipeState: MutableLiveData<RecipeUiState> get() = _recipeState

    init {
        _recipeState.value = RecipeUiState(false, 12)
        Log.i("!!!", _recipeState.value.toString())
    }
}