package com.example.androidsecretsprint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidsecretsprint.databinding.FragmentRecipeBinding

class RecipeFragment : Fragment(R.layout.fragment_recipe) {
    private lateinit var binding: FragmentRecipeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }
}