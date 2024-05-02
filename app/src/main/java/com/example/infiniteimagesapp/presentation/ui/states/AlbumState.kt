package com.example.infiniteimagesapp.presentation.ui.states

import com.example.infiniteimagesapp.domain.mapper.Album

data class AlbumState(
    val albums: List<Album> = emptyList(),
    val isLoading: Boolean = true,
    val error: String = ""
)