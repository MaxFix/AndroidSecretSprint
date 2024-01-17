package com.example.androidsecretsprint.ui.recipies.recipe

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidsecretsprint.model.Recipe

data class RecipeUiState(
    val favoriteRecipe: Boolean,
    val portionsCount: Int,
)

class RecipeViewModel(
    val recipe: Recipe? = null,
    val recipeDrawable: Drawable? = null,
    val favoriteRecipe: Boolean = false,
    val portionsCount: Int? = null,
) : ViewModel() {
    private val _liveData: LiveData<String>? = null

    val liveData: LiveData<String>?
        get() = _liveData

    val currentRecipe: LiveData<Recipe> by lazy {
        MutableLiveData<Recipe>()
    }
    val currentRecipeDrawable: LiveData<Drawable> by lazy {
        MutableLiveData<Drawable>()
    }
    val currentFavoriteRecipe: LiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val currentPortionsCount: LiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
}