package com.example.infiniteimagesapp.di

import com.example.infiniteimagesapp.presentation.feature.albums.di.featureAlbumsModule

val appComponent = listOf(
    appModule,
    createNetworkModule,
    createLocalDBModule,
    repositoryModule,
    featureAlbumsModule
)