package com.example.androidsecretsprint.ui.categories

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidsecretsprint.data.STUB
import com.example.androidsecretsprint.model.Category

data class CategoriesListUiState(
    val dataSet: List<Category> = emptyList(),
)

class CategoriesListViewModel(application: Application) : AndroidViewModel(application) {
    private val _categoriesListState = MutableLiveData<CategoriesListUiState>()
    val categoriesListState: LiveData<CategoriesListUiState> = _categoriesListState

    fun loadCategories() {
        //load from network
        val categories = STUB.getCategories()

        _categoriesListState.value = CategoriesListUiState().copy(
            dataSet = categories,
        )
    }
}