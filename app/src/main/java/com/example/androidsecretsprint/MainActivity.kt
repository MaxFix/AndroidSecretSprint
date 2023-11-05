package com.example.androidsecretsprint

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.androidsecretsprint.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val fragment = CategoriesListFragment()
        val fragmentManager : FragmentManager = supportFragmentManager
        val mainFragment : FragmentTransaction = fragmentManager.beginTransaction()

        mainFragment.replace(R.id.mainContainer, fragment)
        mainFragment.addToBackStack(null)
        mainFragment.commit()
    }
}