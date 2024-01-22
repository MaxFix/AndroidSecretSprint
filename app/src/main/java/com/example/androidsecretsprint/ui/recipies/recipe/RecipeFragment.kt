package com.example.androidsecretsprint.ui.recipies.recipe

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsecretsprint.R
import com.example.androidsecretsprint.data.Constants.Companion.ARG_RECIPE_ID
import com.example.androidsecretsprint.databinding.FragmentRecipeBinding
import com.example.androidsecretsprint.model.Recipe
import java.io.InputStream

class RecipeFragment : Fragment(R.layout.fragment_recipe) {
    private lateinit var binding: FragmentRecipeBinding
    private lateinit var seekBar: SeekBar
    private var ingredientsAdapter: IngredientsAdapter? = null
    private val recipe: RecipeViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipeParcelable = getRecipeFromArguments()
        recipeParcelable?.id?.let { recipe.loadRecipe(it) }
        setupUI(recipeParcelable)
        initRecycler(recipeParcelable)
    }

    private fun getRecipeFromArguments(): Recipe? {
        return if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_RECIPE_ID, Recipe::class.java)
        } else {
            arguments?.getParcelable(ARG_RECIPE_ID)
        }
    }

    private fun setupUI(recipe: Recipe?) {
        this.recipe.recipeState.observe(viewLifecycleOwner) {
            recipe?.let { it ->
                binding.tvRecipeHeaderText.text = it.title
                val inputStream: InputStream? = it.imageUrl.let { imgUrl -> context?.assets?.open(imgUrl) }
                val drawable = Drawable.createFromStream(inputStream, null)
                binding.ivRecipeHeaderImg.setImageDrawable(drawable)

                val isFavorite = this.recipe.recipeState.value?.isFavorite
                val favoritesButton: ImageButton = binding.ibFavorites
                val favoriteIconRes = if (isFavorite == true) R.drawable.ic_heart else R.drawable.ic_heart_empty
                favoritesButton.setBackgroundResource(favoriteIconRes)

                favoritesButton.setOnClickListener {
                    this.recipe.onFavoritesClicked()
                }
            }
        }
    }

    private fun initRecycler(recipe: Recipe?) {
        ingredientsAdapter = recipe?.ingredients?.let { IngredientsAdapter(it) }

        val seekBarListener = IngredientsCountChooseSeekbar(
            onProgressChanged = { progress ->
                binding.tvPortionsCount.text = progress.toString()
                ingredientsAdapter?.updateIngredients(progress)
            },
        )
        seekBar = binding.sbPortionsCount
        seekBar.setPadding(16, 0, 16, 0)
        seekBar.setOnSeekBarChangeListener(seekBarListener)
        binding.rvIngredients.adapter = ingredientsAdapter

        binding.rvIngredients.apply {
            adapter = ingredientsAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(createCustomDivider())
        }

        recipe?.method?.let { method ->
            binding.rvMethod.apply {
                adapter = MethodAdapter(method)
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(createCustomDivider())
            }
        }
    }

    private fun createCustomDivider(): CustomDividerItemDecoration {
        val dividerItemDecoration = context?.let {
            CustomDividerItemDecoration(it, RecyclerView.VERTICAL)
        }
        dividerItemDecoration?.setLastItemDecorated(false)
        return dividerItemDecoration!!
    }
}