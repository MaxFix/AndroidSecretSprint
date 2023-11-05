package com.example.androidsecretsprint

import android.content.res.AssetManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidsecretsprint.databinding.FragmentListCategoriesBinding
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream

class CategoriesListFragment : Fragment(R.layout.fragment_list_categories) {
    private lateinit var binding: FragmentListCategoriesBinding
    private val assets: AssetManager = requireContext().assets

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentListCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun readJSONFromAsset(assetManager: AssetManager, fileName: String): String {
        val inputStream: InputStream = assetManager.open(fileName)
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer)
    }

    // Чтение данных из JSON и создание списка объектов типа Category
    fun createCategoryList(assetManager: AssetManager, fileName: String): List<Category> {
        val jsonString = readJSONFromAsset(assetManager, fileName)
        val jsonArray = JSONArray(jsonString)
        val categories = mutableListOf<Category>()

        for (i in 0 until jsonArray.length()) {
            val item: JSONObject = jsonArray.getJSONObject(i)
            val imageUrl = item.getString("imageUrl").substringAfterLast("/")
            val category = Category(item.getInt("id"), item.getString("title"),
                item.getString("description"), item.getString(imageUrl))
            categories.add(category)
        }

        return categories
    }

    val categories = createCategoryList(assets, "category.json")


}