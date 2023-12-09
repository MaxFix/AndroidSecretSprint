package com.example.androidsecretsprint

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidsecretsprint.databinding.FragmentRecipeBinding

class RecipeFragment : Fragment(R.layout.fragment_recipe) {
    private lateinit var binding: FragmentRecipeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val recipeDescription: TextView = binding.recipeDescription

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
            val recipeParcelable: Recipe? = arguments?.getParcelable("recipe", Recipe::class.java)
            recipeParcelable?.let { recipe: Recipe ->
                //recipeDescription.text = recipe.title
            }
        } else {
            val recipeParcelable = arguments?.getParcelable<Recipe>("recipe")
            //recipeDescription.text = recipeParcelable?.title
        }
    }
}