package com.example.androidsecretsprint

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.io.InputStream

class RecipesListAdapter(
    private val dataSet: List<Recipe>,
    private val fragment: RecipesListFragment,
) :
    RecyclerView.Adapter<RecipesListAdapter.ViewHolder>() {
    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(recipeId: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) { // callback
        itemClickListener = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvRecipeItem: CardView = view.findViewById(R.id.cvRecipeItem)
        val tvRecipeName: TextView = view.findViewById(R.id.tvRecipeName)
        val ivRecipeImage: ImageView = view.findViewById(R.id.ivRecipeImage)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_recipe, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val fragment = fragment
        val recipeTitle = viewHolder.tvRecipeName

        try {
            val inputStream: InputStream? = fragment.context?.assets?.open(dataSet[position].imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            viewHolder.tvRecipeName.text = recipeTitle.text
            viewHolder.ivRecipeImage.setImageDrawable(drawable)
            recipeTitle.text = dataSet[position].title
        } catch (e: Exception) {
            Log.e(
                "!!!", "onBindViewHolder : asset error ${e.printStackTrace()}"
            )

            viewHolder.cvRecipeItem.setOnClickListener {
                itemClickListener?.onItemClick(dataSet[position].id)
            }
        }
    }

    override fun getItemCount() = dataSet.size
}