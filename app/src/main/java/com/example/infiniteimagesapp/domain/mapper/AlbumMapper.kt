package com.example.infiniteimagesapp.domain.mapper

import com.example.infiniteimagesapp.domain.album.AlbumDTO
import com.example.infiniteimagesapp.domain.entities.Album

fun List<AlbumDTO>.toAlbums(): List<Album> {
    return map { albumDTO ->
        Album(
            userId = albumDTO.userId,
            id = albumDTO.id,
            title = albumDTO.title
        )
    }
}