package com.example.androidsecretsprint.ui.recipies.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsecretsprint.R
import com.example.androidsecretsprint.data.Constants.Companion.ARG_RECIPE_ID
import com.example.androidsecretsprint.data.PreferencesRepository
import com.example.androidsecretsprint.databinding.FragmentRecipeBinding
import com.example.androidsecretsprint.model.Recipe

class RecipeFragment : Fragment(R.layout.fragment_recipe) {
    private var binding: FragmentRecipeBinding? = null
    private var seekBar: SeekBar? = null
    private var ingredientsAdapter: IngredientsAdapter? = null
    private var methodAdapter: MethodAdapter? = null
    private val viewModel: RecipeViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return when (modelClass) {
                    RecipeViewModel::class.java -> {
                        RecipeViewModel(PreferencesRepository(requireContext())) as T
                    }

                    else -> {
                        throw IllegalArgumentException("Unknown ViewModel class")
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): NestedScrollView? {
        binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt(ARG_RECIPE_ID)?.let { viewModel.loadRecipe(it) }

        setupUI()
    }

    private fun setupUI() {
        val customDividerItemDecoration = createCustomDivider()
        binding?.rvIngredients?.addItemDecoration(customDividerItemDecoration)
        binding?.rvMethod?.addItemDecoration(customDividerItemDecoration)
        ingredientsAdapter = IngredientsAdapter(listOf())
        methodAdapter = MethodAdapter(listOf())

        viewModel.recipeState.observe(viewLifecycleOwner) { state: RecipeUiState? ->
            val recipe: Recipe? = state?.recipe

            binding?.tvRecipeHeaderText?.text = recipe?.title
            binding?.ivRecipeHeaderImg?.setImageDrawable(state?.recipeDrawable)

            val isFavorite = state?.isFavorite

            val favoritesButton: ImageButton? = binding?.ibFavorites
            val favoriteIconRes = if (isFavorite == true) R.drawable.ic_heart else R.drawable.ic_heart_empty
            favoritesButton?.setBackgroundResource(favoriteIconRes)

            favoritesButton?.setOnClickListener {
                this.viewModel.onFavoritesClicked()
            }

            ingredientsAdapter = recipe?.ingredients?.let { IngredientsAdapter(it) }
            binding?.tvPortionsCount?.text = state?.portionsCount.toString()
            state?.portionsCount?.let { ingredientsAdapter?.updateIngredients(it) }

            val seekBarListener = IngredientsCountChooseSeekbar { progress ->
                viewModel.updatePortionsCountState(progress)
            }
            val dpValue = pxToDp(16)

            seekBar = binding?.sbPortionsCount
            seekBar?.setPadding(dpValue.toInt(), 0, dpValue.toInt(), 0)
            seekBar?.setOnSeekBarChangeListener(seekBarListener)
            binding?.rvIngredients?.adapter = ingredientsAdapter
            binding?.rvMethod?.adapter = methodAdapter

            binding?.rvIngredients?.apply {
                adapter = ingredientsAdapter
                layoutManager = LinearLayoutManager(context)
            }

            binding?.rvMethod?.apply {
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
        return dividerItemDecoration ?: throw IllegalStateException("dividerItemDecoration не должен быть null")
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

    private fun pxToDp(px: Int): Float {
        return px / (resources.displayMetrics.densityDpi / 160f)
    }
}