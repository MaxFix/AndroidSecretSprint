package com.example.androidsecretsprint.ui.recipies.recipe

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

class RecipeFragment : Fragment(R.layout.fragment_recipe) {
    private lateinit var binding: FragmentRecipeBinding
    private lateinit var seekBar: SeekBar
    private var ingredientsAdapter: IngredientsAdapter? = null
    private val viewModel: RecipeViewModel by viewModels()
    //private var customDividerItemDecoration = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt(ARG_RECIPE_ID)?.let { viewModel.loadRecipe(it) }

        setupUI()
    }

    private fun setupUI() {
        val customDividerItemDecoration = createCustomDivider()
        binding.rvIngredients.addItemDecoration(customDividerItemDecoration)
        binding.rvMethod.addItemDecoration(customDividerItemDecoration)
        viewModel.recipeState.observe(viewLifecycleOwner) { state: RecipeUiState? ->
            val recipe: Recipe? = state?.recipe

            binding.tvRecipeHeaderText.text = recipe?.title
            binding.ivRecipeHeaderImg.setImageDrawable(state?.recipeDrawable)

            val isFavorite = state?.isFavorite

            val favoritesButton: ImageButton = binding.ibFavorites
            val favoriteIconRes = if (isFavorite == true) R.drawable.ic_heart else R.drawable.ic_heart_empty
            favoritesButton.setBackgroundResource(favoriteIconRes)

            favoritesButton.setOnClickListener {
                this.viewModel.onFavoritesClicked()
            }

            ingredientsAdapter = recipe?.ingredients?.let { IngredientsAdapter(it) }

            val seekBarListener = IngredientsCountChooseSeekbar(
                onProgressChanged = { portionsCount ->
                    portionsCount.let {
                        binding.tvPortionsCount.text = it.toString()
                        ingredientsAdapter?.updateIngredients(it)
                    }
                    viewModel.updatePortionsCountState(portionsCount)
                }
            )
            seekBar = binding.sbPortionsCount
            seekBar.setPadding(16, 0, 16, 0)
            seekBar.setOnSeekBarChangeListener(seekBarListener)
            binding.rvIngredients.adapter = ingredientsAdapter

            binding.rvIngredients.apply {
                adapter = ingredientsAdapter
                layoutManager = LinearLayoutManager(context)
            }

            recipe?.method?.let { method ->
                binding.rvMethod.apply {
                    adapter = MethodAdapter(method)
                    layoutManager = LinearLayoutManager(context)
                }
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