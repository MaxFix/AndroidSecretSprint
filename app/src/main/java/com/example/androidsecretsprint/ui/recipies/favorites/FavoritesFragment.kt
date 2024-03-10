package com.example.androidsecretsprint.ui.recipies.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidsecretsprint.R
import com.example.androidsecretsprint.data.Constants
import com.example.androidsecretsprint.data.STUB
import com.example.androidsecretsprint.databinding.FragmentFavoritesBinding
import com.example.androidsecretsprint.ui.recipies.recipiesList.RecipesListAdapter

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel: FavoritesViewModel by viewModels {
        FavoritesViewModelFactory(PreferencesRepository(requireContext()))
    }

    private var recipeID: String? = null
    private var recipeTitle: String? = null
    private var recipeImageUrl: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
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
                binding.tvNoData.visibility = View.VISIBLE
                binding.rvFavoriteRecipes.visibility = View.GONE
            } else {
                binding.tvNoData.visibility = View.GONE
                binding.rvFavoriteRecipes.visibility = View.VISIBLE
            }

            val favoritesListAdapter = RecipesListAdapter(favoriteRecipes, this).apply {
                setOnItemClickListener(object : RecipesListAdapter.OnItemClickListener {
                    override fun onItemClick(recipeId: Int) {
                        openRecipeByRecipeId(recipeId)
                    }
                })
            }

            binding.rvFavoriteRecipes.apply {
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

        findNavController().navigate(R.id.action_favoritesFragment_to_recipeFragment, bundle)
    }
}