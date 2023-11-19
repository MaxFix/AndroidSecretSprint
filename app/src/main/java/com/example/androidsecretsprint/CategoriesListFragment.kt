package com.example.androidsecretsprint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.androidsecretsprint.databinding.FragmentListCategoriesBinding


class CategoriesListFragment : Fragment(R.layout.fragment_list_categories) {
    private lateinit var binding: FragmentListCategoriesBinding

    private val categories = listOf<Category>(
        Category(
            0,
            "Бургеры",
            "Рецепты всех популярных видов бургеров",
            "burger.png",
        ),
        Category(
            1,
            "Десерты",
            "Самые вкусные рецепты десертов специально для вас",
            "dessert.png",
        ),
        Category(
            2,
            "Пицца",
            "Пицца на любой вкус и цвет. Лучшая подборка для тебя",
            "pizza.png",
        ),
        Category(
            3,
            "Рыба",
            "Печеная, жареная, сушеная, любая рыба на твой вкус",
            "fish.png",
        ),
        Category(
            4,
            "Супы",
            "От классики до экзотики: мир в одной тарелке",
            "soup.png",
        ),
        Category(
            5,
            "Салаты",
            "Хрустящий калейдоскоп под соусом вдохновения",
            "salad.png",
        ),
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentListCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        val categoriesListAdapter = CategoriesListAdapter(categories, context = this)
        val recyclerView = binding.rvCategories
        recyclerView.adapter = categoriesListAdapter

        categoriesListAdapter.setOnItemClickListener(object : CategoriesListAdapter.OnItemClickListener {
            override fun onItemClick() {
                openRecipesByCategoryId()
            }
        })
    }

    fun openRecipesByCategoryId() {
        childFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipesListFragment>(R.id.tvCategoriesList)
            addToBackStack(null)
        }
    }
}