package com.example.androidsecretsprint

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView


class CategoriesListAdapter(
    private val dataSet: List<Category>,
    val context: CategoriesListFragment,
) :
    RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvCategoryItem: CardView = view.findViewById(R.id.cvCategoryItem)
        val tvCategoryName: TextView = view.findViewById(R.id.tvCategoryName)
        val tvCategoryDescription: TextView = view.findViewById(R.id.tvCategoryDescription)
        val ivCategoryImage: ImageView = view.findViewById(R.id.ivCategoryImage)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_layout, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val categoryTitle = viewHolder.tvCategoryName
        val categoryDescription = viewHolder.tvCategoryDescription
        val assetManager = context.requireContext().assets
        categoryTitle.text = dataSet[position].title
        categoryDescription.text = dataSet[position].description

        try {
            val inputStream = assetManager.open(dataSet[position].imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            viewHolder.ivCategoryImage.setImageDrawable(drawable)
            viewHolder.tvCategoryName.text = categoryTitle.text
            viewHolder.tvCategoryDescription.text = categoryDescription.text

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount() = dataSet.size
}