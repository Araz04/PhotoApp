package com.example.infiniteimagesapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.infiniteimagesapp.domain.mapper.AlbumWithPhotos
import com.example.infiniteimagesapp.domain.mapper.Album
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {
    @Query("SELECT * FROM albums")
    fun getAllAlbums(): Flow<List<Album>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(album: Album)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(albums: List<Album>)

    @Delete
    suspend fun deleteAlbum(album: Album)

    @Query("DELETE FROM albums")
    suspend fun deleteAllAlbums()

    @Query("SELECT * FROM albums")
    fun getAllAlbumsWithPhotos(): List<AlbumWithPhotos>
}