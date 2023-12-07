package com.example.androidsecretsprint

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.androidsecretsprint.databinding.FragmentRecipeBinding

class RecipeFragment : Fragment(R.layout.fragment_recipe) {
    private lateinit var binding: FragmentRecipeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipeDescriptionDeprecated: TextView = binding.recipeDescriptionDeprecated
        val recipeDescriptionNew: TextView = binding.recipeDescriptionNew

        val bundle = Bundle()

        val recipeDeprecated = arguments?.getParcelable<Recipe>("recipe")
        val recipeNew: Recipe? = bundle.getParcelable("recipe", Recipe::class.java)
        recipeNew?.let { recipe: Recipe ->
            recipeDescriptionNew.text = recipe.title
        }

        recipeDescriptionDeprecated.text = recipeDeprecated?.title
    }
}