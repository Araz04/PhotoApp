package com.example.infiniteimagesapp.presentation.feature.albums.di

import com.example.infiniteimagesapp.presentation.feature.albums.AlbumsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureAlbumsModule = module {
    viewModel { AlbumsViewModel(get(), get()) }
}