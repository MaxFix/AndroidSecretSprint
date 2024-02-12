package com.example.androidsecretsprint.ui.recipies.favorites

import android.content.Context
import com.example.androidsecretsprint.data.Constants

class PreferencesRepository(context: Context) {
    private val sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFS_RECIPES, Context.MODE_PRIVATE)

    fun getFavorites(): Set<String> {
        return sharedPreferences.getStringSet(Constants.SHARED_PREFS_RECIPES_DATA, setOf()) ?: setOf()
    }
}