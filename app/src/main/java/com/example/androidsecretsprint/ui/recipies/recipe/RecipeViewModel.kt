package com.example.androidsecretsprint.ui.recipies.recipe

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidsecretsprint.data.PreferencesRepository
import com.example.androidsecretsprint.data.STUB
import com.example.androidsecretsprint.model.Recipe

data class RecipeUiState(
    var recipe: Recipe? = null,
    var portionsCount: Int = 1,
    var isFavorite: Boolean = false,
    val recipeDrawable: Drawable? = null,
)

class RecipeViewModel(private val repository: PreferencesRepository) :
    ViewModel() {
    private val _recipeState = MutableLiveData<RecipeUiState>()
    val recipeState: LiveData<RecipeUiState> = _recipeState

    fun loadRecipe(recipeId: Int) {
        //load from network
        val recipe = STUB.getRecipeById(recipeId)
        val drawable: Drawable? = repository.getDrawableFromAssets(recipe.imageUrl)
        _recipeState.value = RecipeUiState().copy(
            recipe = recipe,
            portionsCount = recipeState.value?.portionsCount ?: 1,
            isFavorite = repository.getFavorites().contains(recipeId.toString()),
            recipeDrawable = drawable
        )
    }

//    private fun getFavorites(): MutableSet<String> {
//        val sharedPrefs =
//            application.getSharedPreferences(Constants.SHARED_PREFS_RECIPES, Context.MODE_PRIVATE) //old set
//        val favoritesRecipe = sharedPrefs?.getStringSet(Constants.SHARED_PREFS_RECIPES_DATA, null)
//        return favoritesRecipe?.let { HashSet(it) } ?: hashSetOf()
//    }

    fun onFavoritesClicked() {
        val isFavorite = repository.getFavorites().contains(recipeState.value?.recipe?.id.toString())
        val recipeId = recipeState.value?.recipe?.id.toString()
        val favoritesSet = repository.getFavorites().toMutableSet()

        if (isFavorite) {
            repository.saveFavorites(favoritesSet.apply { remove(recipeId) })
            _recipeState.value = _recipeState.value?.copy(
                isFavorite = false
            )
        } else {
            repository.saveFavorites(favoritesSet.apply { add(recipeId) })
            _recipeState.value = _recipeState.value?.copy(
                isFavorite = true
            )
        }
    }

    fun updatePortionsCountState(newPortionsCount: Int) {
        _recipeState.value = _recipeState.value?.copy(portionsCount = newPortionsCount)
    }
}