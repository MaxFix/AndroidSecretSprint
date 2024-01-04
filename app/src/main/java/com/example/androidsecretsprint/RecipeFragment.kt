package com.example.androidsecretsprint

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsecretsprint.Constants.Companion.ARG_RECIPE
import com.example.androidsecretsprint.databinding.FragmentRecipeBinding
import java.io.InputStream

class RecipeFragment : Fragment(R.layout.fragment_recipe) {
    private lateinit var binding: FragmentRecipeBinding
    private lateinit var seekBar: SeekBar
    private var ingredientsAdapter: IngredientsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipeParcelable = getRecipeFromArguments()
        setupUI(recipeParcelable)
        initRecycler(recipeParcelable)
    }

    private fun getRecipeFromArguments(): Recipe? {
        return if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_RECIPE, Recipe::class.java)
        } else {
            arguments?.getParcelable(ARG_RECIPE)
        }
    }

    private fun setupUI(recipe: Recipe?) {
        recipe?.let { it ->
            binding.tvRecipeHeaderText.text = it.title
            val inputStream: InputStream? = it.imageUrl.let { imgUrl -> context?.assets?.open(imgUrl) }
            val drawable = Drawable.createFromStream(inputStream, null)
            binding.ivRecipeHeaderImg.setImageDrawable(drawable)

            val favoritesSet = getFavorites()
            val isFavorite = favoritesSet.contains(it.toString())

            val favoriteIconRes = if (isFavorite) R.drawable.ic_heart else R.drawable.ic_heart_empty
            binding.ibFavorites.setBackgroundResource(favoriteIconRes)

            val favoritesButton: ImageButton = binding.ibFavorites
            favoritesButton.setBackgroundResource(R.drawable.ic_heart_empty)
            favoritesButton.setOnClickListener {
                if (isFavorite) {
                    removeFavorite(it.id.toString())
                    favoritesButton.setBackgroundResource(R.drawable.ic_heart_empty)
                } else {
                    saveFavorite(it.id.toString())
                    favoritesButton.setBackgroundResource(R.drawable.ic_heart)
                }
            }
        }
    }

    private fun initRecycler(recipe: Recipe?) {
        ingredientsAdapter = recipe?.ingredients?.let { IngredientsAdapter(it) }

        val seekBarListener = IngredientsCountChooseSeekbar(
            onProgressChanged = { progress ->
                binding.tvPortionsCount.text = progress.toString()
                ingredientsAdapter?.updateIngredients(progress)
            },
        )
        seekBar = binding.sbPortionsCount
        seekBar.setPadding(16, 0, 16, 0)
        seekBar.setOnSeekBarChangeListener(seekBarListener)
        binding.rvIngredients.adapter = ingredientsAdapter

        binding.rvIngredients.apply {
            adapter = ingredientsAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(createCustomDivider())
        }

        recipe?.method?.let { method ->
            binding.rvMethod.apply {
                adapter = MethodAdapter(method)
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(createCustomDivider())
            }
        }
    }

    private fun createCustomDivider(): CustomDividerItemDecoration {
        val dividerItemDecoration = context?.let {
            CustomDividerItemDecoration(it, RecyclerView.VERTICAL)
        }
        dividerItemDecoration?.setLastItemDecorated(false)
        return dividerItemDecoration!!
    }

    private fun saveFavorites(recipesIds: Set<String>) {
        val sharedPrefs = activity?.getSharedPreferences(Constants.SHARED_PREFS_RECIPES, Context.MODE_PRIVATE) ?: return
        val editor = sharedPrefs.edit()
        editor.putStringSet(Constants.SHARED_PREFS_RECIPES_DATA, recipesIds)
        editor.apply()
    }

    private fun getFavorites(): MutableSet<String> {
        val sharedPrefs = activity?.getSharedPreferences(Constants.SHARED_PREFS_RECIPES, Context.MODE_PRIVATE) //old set
        val favoritesRecipe = sharedPrefs?.getStringSet(Constants.SHARED_PREFS_RECIPES_DATA, null)
        return favoritesRecipe?.let { HashSet(it) } ?: hashSetOf()
    }

    private fun saveFavorite(recipeId: String) {
        val favorites = getFavorites().apply { add(recipeId) }
        saveFavorites(favorites)
    }

    private fun removeFavorite(recipeId: String) {
        val favorites = getFavorites().apply { remove(recipeId) }
        saveFavorites(favorites)
    }
}