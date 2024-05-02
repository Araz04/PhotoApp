package com.example.infiniteimagesapp.presentation.ui.states

import com.example.infiniteimagesapp.domain.modles.AlbumWithPhotos

data class State(
    val albums: List<AlbumWithPhotos> = emptyList(),
    val isLoading: Boolean = true,
    val error: String = ""
)