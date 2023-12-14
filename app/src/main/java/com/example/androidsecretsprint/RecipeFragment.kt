package com.example.androidsecretsprint

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsecretsprint.databinding.FragmentRecipeBinding
import java.io.InputStream

class RecipeFragment : Fragment(R.layout.fragment_recipe) {
    private lateinit var binding: FragmentRecipeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipeDescription: TextView = binding.tvRecipeHeaderText
        val recipeHeaderImage: ImageView = binding.ivRecipeHeaderImg

        val fragment = context

        val recipeParcelable = if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(Constants.ARG_RECIPE, Recipe::class.java)
        } else {
            arguments?.getParcelable(Constants.ARG_RECIPE)
        }

        recipeParcelable?.let { recipe ->
            recipeDescription.text = recipe.title
            val inputStream: InputStream? = recipeParcelable.imageUrl.let { fragment?.assets?.open(it) }
            val drawable = Drawable.createFromStream(inputStream, null)
            recipeHeaderImage.setImageDrawable(drawable)
        }


        val ingredientsAdapter = recipeParcelable?.let { IngredientsAdapter(it.ingredients) }
        val recyclerViewIngredients = binding.rvIngredients

        val dividerItemDecorationIngredients = context?.let { CustomDividerItemDecoration(it, RecyclerView.VERTICAL) }
        val divider = ContextCompat.getDrawable(requireContext(), R.drawable.custom_divider)
        dividerItemDecorationIngredients?.setDrawable(divider!!)
        dividerItemDecorationIngredients?.setLastItemDecorated(false)
        if (dividerItemDecorationIngredients != null) {
            recyclerViewIngredients.addItemDecoration(dividerItemDecorationIngredients)
        }

        recyclerViewIngredients.adapter = ingredientsAdapter
        recyclerViewIngredients.layoutManager = LinearLayoutManager(context)


        val methodAdapter = recipeParcelable?.let { MethodAdapter(it.method) }
        val recyclerViewMethods = binding.rvMethod

        val dividerItemDecorationMethods = context?.let { CustomDividerItemDecoration(it, RecyclerView.VERTICAL) }
        dividerItemDecorationMethods?.setDrawable(divider!!)
        dividerItemDecorationMethods?.setLastItemDecorated(false)
        if (dividerItemDecorationMethods != null) {
            recyclerViewMethods.addItemDecoration(dividerItemDecorationMethods)
        }

        recyclerViewMethods.adapter = methodAdapter
        recyclerViewMethods.layoutManager = LinearLayoutManager(context)
    }

}