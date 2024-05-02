package com.example.infiniteimagesapp.domain.mapper

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.infiniteimagesapp.domain.album.AlbumDTO

@Entity(tableName = "albums")
data class Album(
     val userId: Int,
      val id: Int,
     @PrimaryKey val title: String
)

fun List<AlbumDTO>.toAlbums(): List<Album> {
    return map { albumDTO ->
        Album(
            userId = albumDTO.userId,
            id = albumDTO.id,
            title = albumDTO.title
        )
    }
}