package com.example.androidsecretsprint

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.androidsecretsprint.Constants.Companion.ARG_CATEGORY_ID
import com.example.androidsecretsprint.Constants.Companion.ARG_CATEGORY_IMAGE_URL
import com.example.androidsecretsprint.Constants.Companion.ARG_CATEGORY_NAME
import com.example.androidsecretsprint.Constants.Companion.ARG_RECIPE
import com.example.androidsecretsprint.Constants.Companion.ARG_RECIPE_ID
import com.example.androidsecretsprint.Constants.Companion.ARG_RECIPE_IMAGE_URL
import com.example.androidsecretsprint.Constants.Companion.ARG_RECIPE_NAME
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
        initArgs()
        initUI()

        val inputStream: InputStream? = recipeImageUrl?.let { this.context?.assets?.open(it) }
        val drawable = Drawable.createFromStream(inputStream, null)
        binding.recipesListHeaderImg.setImageDrawable(drawable)
        binding.recipesListHeaderText.text = recipeTitle
    }

    private fun initArgs() {
        recipeID = arguments?.getString(ARG_CATEGORY_ID)
        recipeTitle = arguments?.getString(ARG_CATEGORY_NAME)
        recipeImageUrl = arguments?.getString(ARG_CATEGORY_IMAGE_URL)
    }

    private fun initUI() {
        val inputStream: InputStream? = recipeImageUrl?.let { this.context?.assets?.open(it) }
        val drawable = Drawable.createFromStream(inputStream, null)
        binding.recipesListHeaderImg.setImageDrawable(drawable)
        binding.recipesListHeaderText.text = recipeTitle
    }

    private fun initRecycler() {
        val recipesListAdapter = arguments?.getInt(ARG_CATEGORY_ID)
            ?.let { STUB.getRecipesByCategoryId(it) }?.let { RecipesListAdapter(it, fragment = this) }
        val recyclerView = binding.rvRecipes
        recyclerView.adapter = recipesListAdapter
        recipesListAdapter?.setOnItemClickListener(object : RecipesListAdapter.OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        }
        )
    }

    fun openRecipeByRecipeId(recipeId: Int) {
        val recipe = STUB.getRecipeById(recipeId) //burgerRecipes[recipeId]
        val bundle = bundleOf(
            ARG_RECIPE_ID to recipeId,
            ARG_RECIPE_NAME to recipeTitle,
            ARG_RECIPE_IMAGE_URL to recipeImageUrl
        )

        bundle.putParcelable(ARG_RECIPE, recipe)

        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            addToBackStack(null)
        }
    }
}