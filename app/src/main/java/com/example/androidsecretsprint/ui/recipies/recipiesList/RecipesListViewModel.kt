package com.example.androidsecretsprint.ui.recipies.recipiesList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidsecretsprint.data.STUB
import com.example.androidsecretsprint.model.Recipe

data class RecipesListUiState(
    val dataSetRecipesList: List<Recipe> = emptyList(),
    var categoryId: Int? = null,
    var categoryName: String? = null,
    var categoryImageUrl: String? = null,
)

class RecipesListViewModel : ViewModel() {
    private val _recipesListState = MutableLiveData<RecipesListUiState>()
    val recipesListState: LiveData<RecipesListUiState> = _recipesListState

    fun loadRecipes(categoryId: Int?) {
        val category = STUB.getCategories().find { it.id == categoryId }

        val recipesListFromCategory = category?.let { STUB.getRecipesByCategoryId(it.id) }
        _recipesListState.value = recipesListFromCategory?.let {
            RecipesListUiState(
                dataSetRecipesList = it,
                categoryName = recipesListState.value?.categoryName,
                categoryImageUrl = recipesListState.value?.categoryImageUrl
            )
        }
    }
}