package com.example.androidsecretsprint.ui.recipies.recipe

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.androidsecretsprint.R

class MethodAdapter(
    var dataSet: List<String>,
) :
    RecyclerView.Adapter<MethodAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val methodNumber: TextView = view.findViewById(R.id.tvRecipeMethodDescription)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_method, viewGroup, false) // хз что тут вместо item_recipe
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val index = position + 1
        val methodDescription = viewHolder.methodNumber

        viewHolder.methodNumber.text = methodDescription.text
        viewHolder.methodNumber.setTextColor(
            ContextCompat.getColor(
                viewHolder.itemView.context,
                R.color.description_categories_color
            )
        )
        methodDescription.text = "$index. ${dataSet[position]}"
        methodDescription.textSize = 14F
    }

    override fun getItemCount() = dataSet.size
}