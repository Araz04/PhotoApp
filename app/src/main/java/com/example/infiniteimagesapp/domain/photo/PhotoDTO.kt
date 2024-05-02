package com.example.infiniteimagesapp.domain.photo

data class PhotoDTO(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)