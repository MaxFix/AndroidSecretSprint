package com.example.androidsecretsprint

import STUB_RECIPES
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.androidsecretsprint.databinding.FragmentRecipesListBinding
import java.io.InputStream

class RecipesListFragment : Fragment(R.layout.fragment_recipes_list) {
    private lateinit var binding: FragmentRecipesListBinding

    private var recipeID: String? = null
    private var recipeTitle: String? = null
    private var recipeImageUrl: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRecipesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()

        recipeID = arguments?.let { Constants.ARG_RECIPE_ID }
        recipeTitle = arguments?.let { Constants.ARG_RECIPE_NAME }
        recipeImageUrl = arguments?.let { Constants.ARG_RECIPE_IMAGE_URL }

        val fragment = context
        val inputStream: InputStream? = fragment?.assets?.open("bcg_recipes_list.png")
        val drawable = Drawable.createFromStream(inputStream, null)
        binding.recipesListHeaderImg.setImageDrawable(drawable)
    }

    private fun initRecycler() {
        val recipesListAdapter = RecipesListAdapter(STUB_RECIPES.burgerRecipes, fragment = this)
        val recyclerView = binding.rvRecipes
        recyclerView.adapter = recipesListAdapter
        recipesListAdapter.setOnItemClickListener(object : RecipesListAdapter.OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        }
        )
    }

    fun openRecipeByRecipeId(recipeId: Int) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipeFragment>(R.id.mainContainer)
            addToBackStack(null)
        }
    }
}