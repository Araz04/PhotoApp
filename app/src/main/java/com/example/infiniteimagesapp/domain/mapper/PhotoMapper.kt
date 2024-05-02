package com.example.infiniteimagesapp.domain.mapper

import com.example.infiniteimagesapp.domain.photo.PhotoDTO
import com.example.infiniteimagesapp.domain.entities.Photo

fun List<PhotoDTO>.toPhotos(): List<Photo> {
    return map { photoDTO ->
        Photo(
            albumId = photoDTO.albumId,
            id = photoDTO.id,
            thumbnailUrl = photoDTO.thumbnailUrl,
            title = photoDTO.title,
            url = photoDTO.url
        )
    }
}