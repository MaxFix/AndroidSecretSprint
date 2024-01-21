package com.example.androidsecretsprint.ui.recipies.recipe

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidsecretsprint.data.Constants
import com.example.androidsecretsprint.data.STUB
import com.example.androidsecretsprint.model.Recipe

data class RecipeUiState(
    var recipe: Recipe? = null,
    var portionsCount: Int = 1,
    var isFavorite: Boolean = false,
    val recipeDrawable: Drawable? = null,
)

class RecipeViewModel(private val application: Application) : AndroidViewModel(application) {
    private val _recipeState = MutableLiveData<RecipeUiState>()
    val recipeState: LiveData<RecipeUiState> = _recipeState

    init {
        _recipeState.value = RecipeUiState(isFavorite = true)
        Log.i("!!!", _recipeState.value.toString())
    }

    fun loadRecipe(recipeId: Int) {
        //load from network
        val recipe = STUB.getRecipeById(recipeId)
        _recipeState.value = _recipeState.value?.copy(
            recipe = recipe,
            portionsCount = recipeState.value?.portionsCount ?: 1,
            isFavorite = getFavorites().contains(recipeId.toString())
        )
    }

    fun getFavorites(): MutableSet<String> {
        val sharedPrefs =
            application.getSharedPreferences(Constants.SHARED_PREFS_RECIPES, Context.MODE_PRIVATE) //old set
        val favoritesRecipe = sharedPrefs?.getStringSet(Constants.SHARED_PREFS_RECIPES_DATA, null)
        return favoritesRecipe?.let { HashSet(it) } ?: hashSetOf()
    }

    fun onFavoritesClicked() {
        val isFavorite = getFavorites().contains(recipeState.value?.recipe?.id.toString())
        val recipeId = recipeState.value?.recipe?.id.toString()
        val favoritesSet = getFavorites()

        if (isFavorite) {
            saveFavorites(favoritesSet.apply { remove(recipeId) })
            recipeState.value?.isFavorite = false
        } else {
            saveFavorites(favoritesSet.apply { add(recipeId) })
            recipeState.value?.isFavorite = true
        }
    }

    private fun saveFavorites(recipesIds: Set<String>) {
        val sharedPrefs =
            application.getSharedPreferences(Constants.SHARED_PREFS_RECIPES, Context.MODE_PRIVATE)
        with(sharedPrefs.edit()) {
            putStringSet(Constants.SHARED_PREFS_RECIPES_DATA, recipesIds)
            apply()
        }
    }
}