package com.example.androidsecretsprint.ui.recipies.recipiesList

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsecretsprint.R
import com.example.androidsecretsprint.model.Recipe
import java.io.InputStream

class RecipesListAdapter(
    private val dataSet: List<Recipe>,
    private val fragment: Fragment,
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
        val recipeTitle = viewHolder.tvRecipeName

        try {
            val inputStream: InputStream? = fragment.context?.assets?.open(dataSet[position].imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            viewHolder.ivRecipeImage.setImageDrawable(drawable)
            recipeTitle.text = dataSet[position].title
        } catch (e: Exception) {
            Log.e(
                "!!!", "onBindViewHolder : asset error ${e.printStackTrace()}"
            )
        }
        viewHolder.cvRecipeItem.setOnClickListener {
            itemClickListener?.onItemClick(dataSet[position].id)
        }
    }

    override fun getItemCount() = dataSet.size
}