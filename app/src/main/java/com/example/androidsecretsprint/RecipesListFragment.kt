package com.example.androidsecretsprint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidsecretsprint.databinding.FragmentRecipesListBinding

class RecipesListFragment : Fragment(R.layout.fragment_recipes_list) {
    private lateinit var binding: FragmentRecipesListBinding

    private var recipeID: Int? = null
    private var recipeTitle: String? = null
    private var recipeImageUrl: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRecipesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = binding.tvCategoriesList
        textView.text
        recipeID = requireArguments().getInt("CATEGORY_ID")
        recipeTitle = requireArguments().getString("CATEGORY_NAME")
        recipeImageUrl = requireArguments().getString("CATEGORY_IMAGE_URL")
    }
}