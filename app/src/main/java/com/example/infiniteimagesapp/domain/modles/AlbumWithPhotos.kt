package com.example.infiniteimagesapp.domain.modles

import androidx.room.Embedded
import androidx.room.Relation
import com.example.infiniteimagesapp.domain.entities.Album
import com.example.infiniteimagesapp.domain.entities.Photo

data class AlbumWithPhotos(@Embedded val album: Album,
                           @Relation(
                                parentColumn = "id",
                                entityColumn = "albumId"
                            )
                           val photos: MutableList<Photo> = mutableListOf()
)
