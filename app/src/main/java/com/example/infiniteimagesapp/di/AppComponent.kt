package com.example.infiniteimagesapp.di

import com.example.infiniteimagesapp.presentation.feature.albums.di.featureAlbumsModule
import com.example.infiniteimagesapp.presentation.feature.favorites.di.featureFavoritesModule

val appComponent = listOf(
    appModule,
    createNetworkModule,
    createLocalDBModule,
    repositoryModule,
    featureAlbumsModule,
    featureFavoritesModule
)