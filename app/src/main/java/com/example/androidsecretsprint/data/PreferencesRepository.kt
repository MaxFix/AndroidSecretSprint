package com.example.androidsecretsprint.data

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.edit
import java.io.IOException

class PreferencesRepository(context: Context) {
    private val sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFS_RECIPES, Context.MODE_PRIVATE)
    private val appContext = context.applicationContext

    fun getFavorites(): Set<String> {
        return sharedPreferences.getStringSet(Constants.SHARED_PREFS_RECIPES_DATA, setOf()) ?: setOf()
    }

    fun getRecipesList(): Int {
        return sharedPreferences.getInt(Constants.ARG_CATEGORY_ID, 0)
    }

    fun saveFavorites(recipesIds: Set<String>) {
        sharedPreferences.edit() {
            putStringSet(Constants.SHARED_PREFS_RECIPES_DATA, recipesIds)
            apply()
        }
    }

    fun getDrawableFromAssets(assetPath: String): Drawable? {
        return try {
            val inputStream = appContext.assets.open(assetPath)
            Drawable.createFromStream(inputStream, null).also {
                inputStream.close()
            }
        } catch (e: IOException) {
            null
        }
    }
}