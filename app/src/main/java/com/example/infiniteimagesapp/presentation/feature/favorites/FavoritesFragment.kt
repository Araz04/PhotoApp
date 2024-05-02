package com.example.infiniteimagesapp.presentation.feature.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.infiniteimagesapp.common.base.BaseFragment
import com.example.infiniteimagesapp.databinding.FragmentFavoritesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment: BaseFragment() {
    private val mViewModel: FavoritesViewModel by viewModel()
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun getViewModel() = mViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView(){

    }

    private fun initData(){

    }
}