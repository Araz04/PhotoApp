package com.example.infiniteimagesapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.infiniteimagesapp.domain.entities.Photo
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {
    @Query("SELECT * FROM photos WHERE albumId = :albumId")
    fun getPhotosByAlbumId(albumId: Int): Flow<List<Photo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPhotos(photos: List<Photo>)

    @Query("DELETE FROM photos")
    suspend fun deleteAllPhotos()
}