package com.example.androidsecretsprint.ui.recipies.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidsecretsprint.R
import com.example.androidsecretsprint.data.CommonViewModelFactory
import com.example.androidsecretsprint.data.Constants
import com.example.androidsecretsprint.data.PreferencesRepository
import com.example.androidsecretsprint.data.STUB
import com.example.androidsecretsprint.databinding.FragmentFavoritesBinding
import com.example.androidsecretsprint.ui.recipies.recipe.RecipeFragment
import com.example.androidsecretsprint.ui.recipies.recipiesList.RecipesListAdapter

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    private var binding: FragmentFavoritesBinding? = null
    private val viewModelFactory = CommonViewModelFactory(mapOf(FavoritesViewModel::class.java to {
        FavoritesViewModel(PreferencesRepository(requireContext()))
    }), requireContext())
    private val viewModel: FavoritesViewModel by viewModels {
        viewModelFactory { viewModelFactory }
    }

    private var recipeID: String? = null
    private var recipeTitle: String? = null
    private var recipeImageUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): LinearLayoutCompat? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadFavorites()

        arguments.let {
            recipeID = Constants.ARG_CATEGORY_ID
        }
        initUI()
    }

    private fun initUI() {
        viewModel.favoritesState.observe(viewLifecycleOwner) { state: FavoritesUiState ->
            val favoriteRecipes = state.dataSet

            if (favoriteRecipes.isEmpty()) {
                binding?.tvNoData?.visibility = View.VISIBLE
                binding?.rvFavoriteRecipes?.visibility = View.GONE
            } else {
                binding?.tvNoData?.visibility = View.GONE
                binding?.rvFavoriteRecipes?.visibility = View.VISIBLE
            }

            val favoritesListAdapter = RecipesListAdapter(favoriteRecipes, this).apply {
                setOnItemClickListener(object : RecipesListAdapter.OnItemClickListener {
                    override fun onItemClick(recipeId: Int) {
                        openRecipeByRecipeId(recipeId)
                    }
                })
            }

            binding?.rvFavoriteRecipes?.apply {
                adapter = favoritesListAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
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