package com.example.androidsecretsprint

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class IngredientsAdapter(
    private val dataSet: List<Ingredient>,
) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ingredientName: TextView = view.findViewById(R.id.tvRecipeIngredientName)
        val ingredientCount: TextView = view.findViewById(R.id.tvRecipeIngredientCount)
        val ingredientMeasure: TextView = view.findViewById(R.id.tvRecipeIngredientMeasure)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_ingredient, viewGroup, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val ingredientName = viewHolder.ingredientName
        val ingredientCount = viewHolder.ingredientCount
        val ingredientMeasure = viewHolder.ingredientMeasure

        try {
            viewHolder.ingredientName.text = ingredientName.text
            viewHolder.ingredientCount.text = ingredientCount.text
            viewHolder.ingredientMeasure.text = ingredientMeasure.text
            viewHolder.ingredientName.setTextColor(
                ContextCompat.getColor(
                    viewHolder.itemView.context,
                    R.color.description_categories_color
                )
            )
            viewHolder.ingredientCount.setTextColor(
                ContextCompat.getColor(
                    viewHolder.itemView.context,
                    R.color.description_categories_color
                )
            )
            viewHolder.ingredientMeasure.setTextColor(
                ContextCompat.getColor(
                    viewHolder.itemView.context,
                    R.color.description_categories_color
                )
            )
            ingredientName.text = dataSet[position].description
            ingredientCount.text = "${dataSet[position].quantity} "
            ingredientMeasure.text = dataSet[position].unitOfMeasure

        } catch (e: Exception) {
            Log.e(
                "!!!", "onBindViewHolder : asset error ${e.printStackTrace()}"
            )
        }
    }

    override fun getItemCount() = dataSet.size

}