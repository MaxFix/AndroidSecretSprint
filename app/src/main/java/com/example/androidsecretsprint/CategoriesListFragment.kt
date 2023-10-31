package com.example.androidsecretsprint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

class CategoriesListFragment : Fragment(R.layout.fragment_list_categories) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: ViewDataBinding? =
            DataBindingUtil.inflate(inflater, R.layout.fragment_list_categories, container, false)
        return binding?.root
    }
}