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
    private var methodAdapter: MethodAdapter? = null
    private val viewModel: RecipeViewModel by viewModels()

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
        ingredientsAdapter = IngredientsAdapter(listOf())
        methodAdapter = MethodAdapter(listOf())

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
            binding.tvPortionsCount.text = state?.portionsCount.toString()
            state?.portionsCount?.let { ingredientsAdapter?.updateIngredients(it) }

            val seekBarListener = IngredientsCountChooseSeekbar { progress ->
                viewModel.updatePortionsCountState(progress)
            }

            seekBar = binding.sbPortionsCount
            seekBar.setPadding(16, 0, 16, 0)
            seekBar.setOnSeekBarChangeListener(seekBarListener)
            binding.rvIngredients.adapter = ingredientsAdapter
            binding.rvMethod.adapter = methodAdapter

            binding.rvIngredients.apply {
                adapter = ingredientsAdapter
                layoutManager = LinearLayoutManager(context)
            }

            binding.rvMethod.apply {
                adapter = methodAdapter
                layoutManager = LinearLayoutManager(context)

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

    class IngredientsCountChooseSeekbar(
        private val onChangeIngredients: (Int) -> Unit
    ) : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            onChangeIngredients.invoke(progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
            // Обработать начало взаимодействия с ползунком
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            // Обработать окончание взаимодействия с ползунком
        }
    }
}