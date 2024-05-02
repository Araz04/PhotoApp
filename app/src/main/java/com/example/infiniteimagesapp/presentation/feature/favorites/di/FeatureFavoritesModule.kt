package com.example.infiniteimagesapp.presentation.feature.favorites.di

import com.example.infiniteimagesapp.presentation.feature.albums.AlbumsViewModel
import com.example.infiniteimagesapp.presentation.feature.favorites.FavoritesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val  featureFavoritesModule = module{
    viewModel { FavoritesViewModel() }
}