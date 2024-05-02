package com.example.infiniteimagesapp.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.infiniteimagesapp.domain.album.AlbumDTO

@Entity(tableName = "albums")
data class Album(
     val userId: Int,
      val id: Int,
     @PrimaryKey val title: String
)

