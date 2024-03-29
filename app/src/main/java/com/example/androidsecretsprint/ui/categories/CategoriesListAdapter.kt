package com.example.androidsecretsprint.ui.categories

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsecretsprint.R
import com.example.androidsecretsprint.model.Category
import java.io.InputStream


class CategoriesListAdapter(
    private val dataSet: List<Category>,
    private val context: CategoriesListFragment,
) :
    RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {
    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(categoryId: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) { // callback
        itemClickListener = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvCategoryItem: CardView = view.findViewById(R.id.cvCategoryItem)
        val tvCategoryName: TextView = view.findViewById(R.id.tvCategoryName)
        val tvCategoryDescription: TextView = view.findViewById(R.id.tvCategoryDescription)
        val ivCategoryImage: ImageView = view.findViewById(R.id.ivCategoryImage)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_category, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val fragment = context
        val categoryTitle = viewHolder.tvCategoryName
        val categoryDescription = viewHolder.tvCategoryDescription

        try {

            val inputStream: InputStream? = fragment.context?.assets?.open(dataSet[position].imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)

            viewHolder.tvCategoryName.text = categoryTitle.text
            viewHolder.tvCategoryDescription.text = categoryDescription.text
            viewHolder.ivCategoryImage.setImageDrawable(drawable)
            categoryTitle.text = dataSet[position].title
            categoryDescription.text = dataSet[position].description
        } catch (e: Exception) {
            Log.e(
                "!!!", "onBindViewHolder : asset error ${e.printStackTrace()}"
            )
        }

        viewHolder.cvCategoryItem.setOnClickListener { //вызов callback'a
            itemClickListener?.onItemClick(dataSet[position].id)
        }
    }

    override fun getItemCount() = dataSet.size
}