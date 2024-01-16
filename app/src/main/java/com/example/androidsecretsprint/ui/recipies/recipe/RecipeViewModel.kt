package com.example.androidsecretsprint.ui.recipies.recipe

import androidx.lifecycle.ViewModel

class RecipeViewModel(
    val favoriteRecipe: Boolean = false,
    val portionsCount: Int? = null,
    val ingredientsCount: Int? = null
) : ViewModel() {

}