package com.example.infiniteimagesapp.domain.mapper

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.infiniteimagesapp.domain.image.PhotoDTO

@Entity(tableName = "photos")
data class Photo(
    val albumId: Int,
    @PrimaryKey val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)

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