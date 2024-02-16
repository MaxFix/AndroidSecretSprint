package com.example.androidsecretsprint.ui.recipies.recipiesList

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidsecretsprint.data.Constants
import com.example.androidsecretsprint.data.STUB
import com.example.androidsecretsprint.model.Recipe
import com.example.androidsecretsprint.ui.recipies.favorites.PreferencesRepository

data class RecipesListUiState(
    val dataSetRecipesList: List<Recipe> = emptyList(),
    val categoryName: String? = null,
    val categoryImageUrl: String? = null,
)

class RecipesListViewModel(private val repository: PreferencesRepository) : ViewModel() {
    private val _recipesListState = MutableLiveData<RecipesListUiState>()
    val recipesListState: LiveData<RecipesListUiState> = _recipesListState

    fun loadRecipes(bundle: Bundle) {
        val categoryId = repository.getRecipesList()
        val recipesListFromCategory = STUB.getRecipesByCategoryId(categoryId)
        _recipesListState.value = RecipesListUiState(
            dataSetRecipesList = recipesListFromCategory,
            categoryName = bundle.getString(Constants.ARG_CATEGORY_NAME),
            categoryImageUrl = bundle.getString(Constants.ARG_CATEGORY_IMAGE_URL)
        )
    }
}