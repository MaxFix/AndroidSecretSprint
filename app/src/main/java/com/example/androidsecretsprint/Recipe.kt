package com.example.androidsecretsprint

data class Recipe(
    val title: String,
    val ingredient: List<List<String>>,
    val method: List<String>,
    val imageUrl: String,
)
