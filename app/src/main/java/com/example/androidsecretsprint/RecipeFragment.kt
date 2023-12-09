package com.example.androidsecretsprint

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
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
        val recipeHeaderTitle: TextView = binding.recipeHeaderText
        val recipeHeaderImage: ImageView = binding.recipeHeaderImg
        val fragment = context

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
            val recipeParcelable: Recipe? = arguments?.getParcelable("recipe", Recipe::class.java)
            recipeParcelable?.let { recipe: Recipe ->
                val inputStream: InputStream? = recipeParcelable.imageUrl.let { fragment?.assets?.open(it) }
                val drawable = Drawable.createFromStream(inputStream, null)
                recipeHeaderImage.setImageDrawable(drawable)
                recipeHeaderTitle.text = recipeParcelable.title

            }
        } else {
            val recipeParcelable = arguments?.getParcelable<Recipe>("recipe")
            val inputStream: InputStream? = recipeParcelable?.imageUrl?.let { fragment?.assets?.open(it) }
            val drawable = Drawable.createFromStream(inputStream, null)
            recipeHeaderImage.setImageDrawable(drawable)
            recipeHeaderTitle.text = recipeParcelable?.title
        }
    }
}