package com.example.infiniteimagesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.infiniteimagesapp.domain.entities.Album
import com.example.infiniteimagesapp.domain.entities.Photo

@Database(entities = [Album::class,  Photo::class], version = 1, exportSchema = false)
abstract class PhotoDatabase: RoomDatabase() {
    abstract val albumDao: AlbumDao
    abstract val photoDao: PhotoDao

    companion object {
        const val DATABASE_NAME = "PHOTO_DATABASE"
    }
}