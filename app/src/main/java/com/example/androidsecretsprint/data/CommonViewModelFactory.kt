package com.example.androidsecretsprint.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CommonViewModelFactory(
    private val creators: Map<Class<out ViewModel>, (Context) -> ViewModel>,
    private val context: Context,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?: creators.asIterable().firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.invoke(context) as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}