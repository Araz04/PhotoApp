package com.example.infiniteimagesapp.domain.image

data class PhotoDTO(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)