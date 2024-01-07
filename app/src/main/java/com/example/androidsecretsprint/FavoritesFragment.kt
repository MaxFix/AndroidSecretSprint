package com.example.androidsecretsprint

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.androidsecretsprint.databinding.FragmentFavoritesBinding
import java.io.InputStream

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    private lateinit var binding: FragmentFavoritesBinding

    private var recipeID: String? = null
    private var recipeTitle: String? = null
    private var recipeImageUrl: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        initArgs()
        initUI()

        val inputStream: InputStream? = recipeImageUrl?.let { this.context?.assets?.open(it) }
        val drawable = Drawable.createFromStream(inputStream, null)
        binding.favoriteRecipesHeaderImg.setImageDrawable(drawable)
        binding.favoritesRecipesHeaderText.text = recipeTitle
    }

    private fun initArgs() {
        recipeID = arguments?.getString(Constants.ARG_CATEGORY_ID)
        recipeTitle = arguments?.getString(Constants.ARG_CATEGORY_NAME)
        recipeImageUrl = arguments?.getString(Constants.ARG_CATEGORY_IMAGE_URL)
    }

    private fun initRecycler() {
        val favoriteRecipeIdsStringSet = RecipeFragment().getFavorites()
        val favoriteRecipeIds = favoriteRecipeIdsStringSet.mapNotNull { idString ->
            idString.toIntOrNull()
        }.toSet()
        val favoriteRecipes = STUB.getRecipesByIds(favoriteRecipeIds)
        val favoritesListAdapter = RecipesListAdapter(favoriteRecipes, this).apply {
            setOnItemClickListener(object : RecipesListAdapter.OnItemClickListener {
                override fun onItemClick(recipeId: Int) {
                    openRecipeByRecipeId(recipeId)
                }
            })
        }
        val recyclerView = binding.rvFavoriteRecipes
        recyclerView.adapter = favoritesListAdapter
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initUI() {
        val drawable: Drawable? = this.context?.getDrawable(R.drawable.bcg_favorites)
        binding.favoriteRecipesHeaderImg.setImageDrawable(drawable)
        binding.favoritesRecipesHeaderText.text = recipeTitle
    }

    fun openRecipeByRecipeId(recipeId: Int) {
        val recipe = STUB.getRecipeById(recipeId) //burgerRecipes[recipeId]
        val bundle = bundleOf(
            Constants.ARG_RECIPE_ID to recipeId,
            Constants.ARG_RECIPE_NAME to recipeTitle,
            Constants.ARG_RECIPE_IMAGE_URL to recipeImageUrl
        )

        bundle.putParcelable(Constants.ARG_RECIPE, recipe)

        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            addToBackStack(null)
        }
    }
}