package com.example.androidsecretsprint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.androidsecretsprint.databinding.FragmentListCategoriesBinding


class CategoriesListFragment : Fragment(R.layout.fragment_list_categories) {
    private lateinit var binding: FragmentListCategoriesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentListCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        val categoriesListAdapter = CategoriesListAdapter(STUB.getCategories(), context = this)
        val recyclerView = binding.rvCategories
        recyclerView.adapter = categoriesListAdapter
        categoriesListAdapter.setOnItemClickListener(object : CategoriesListAdapter.OnItemClickListener {
            override fun onItemClick(categoryId: Int) {
                openRecipesByCategoryId(categoryId)
            }
        })
    }

    fun openRecipesByCategoryId(categoryId: Int) {
        val categories = STUB.getCategories().firstOrNull { it.id == categoryId }
        val categoryName: String? = categories?.title
        val categoryImageUrl: String? = categories?.imageUrl
        val bundle = bundleOf(
            Constants.ARG_CATEGORY_ID to categoryId,
            Constants.ARG_CATEGORY_NAME to categoryName,
            Constants.ARG_CATEGORY_IMAGE_URL to categoryImageUrl
        )
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipesListFragment>(R.id.mainContainer, args = bundle)
            addToBackStack(null)
        }
    }
}