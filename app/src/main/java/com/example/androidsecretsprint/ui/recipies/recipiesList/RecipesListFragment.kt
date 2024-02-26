package com.example.androidsecretsprint.ui.recipies.recipiesList

import android.graphics.drawable.Drawable
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
import com.example.androidsecretsprint.R
import com.example.androidsecretsprint.data.Constants.Companion.ARG_RECIPE
import com.example.androidsecretsprint.data.Constants.Companion.ARG_RECIPE_ID
import com.example.androidsecretsprint.data.PreferencesRepository
import com.example.androidsecretsprint.data.STUB
import com.example.androidsecretsprint.databinding.FragmentRecipesListBinding
import com.example.androidsecretsprint.ui.recipies.recipe.RecipeFragment
import java.io.InputStream

class RecipesListFragment : Fragment(R.layout.fragment_recipes_list) {
    private var binding: FragmentRecipesListBinding? = null
    private val viewModel: RecipesListViewModel by viewModels {
        RecipesListViewModelFactory(PreferencesRepository(requireContext()))
    }

    private var recipeTitle: String? = null
    private var recipeImageUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): LinearLayoutCompat? {
        binding = FragmentRecipesListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = requireArguments()
        viewModel.loadRecipes(bundle)
        viewModel.recipesListState.observe(viewLifecycleOwner) { state: RecipesListUiState ->
            val recipesList = state.dataSetRecipesList
            recipeTitle = state.categoryName
            recipeImageUrl = state.categoryImageUrl

            val inputStream: InputStream? = recipeImageUrl?.let { this.context?.assets?.open(it) }
            val drawable = Drawable.createFromStream(inputStream, null)
            binding?.recipesListHeaderImg?.setImageDrawable(drawable)
            binding?.recipesListHeaderText?.text = recipeTitle

            val recipesListAdapter = RecipesListAdapter(recipesList, this)
            val recyclerView = binding?.rvRecipes
            recyclerView?.adapter = recipesListAdapter
            recipesListAdapter.setOnItemClickListener(object : RecipesListAdapter.OnItemClickListener {
                override fun onItemClick(recipeId: Int) {
                    openRecipeByRecipeId(recipeId)
                }
            }
            )
        }
    }

    fun openRecipeByRecipeId(recipeId: Int) {
        val recipe = STUB.getRecipeById(recipeId) //burgerRecipes[recipeId]
        val bundle = bundleOf(
            ARG_RECIPE_ID to recipeId,
        )

        bundle.putParcelable(ARG_RECIPE, recipe)

        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            addToBackStack(null)
        }
    }
}