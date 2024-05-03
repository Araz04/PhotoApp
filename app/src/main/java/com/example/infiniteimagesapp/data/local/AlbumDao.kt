package com.example.infiniteimagesapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.infiniteimagesapp.domain.modles.AlbumWithPhotos
import com.example.infiniteimagesapp.domain.entities.Album
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {
    @Query("SELECT * FROM albums")
    fun getAllAlbums(): Flow<List<Album>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(albums: List<Album>)

    @Delete
    suspend fun deleteAlbum(album: Album)

    @Query("DELETE FROM albums")
    suspend fun deleteAllAlbums()

    @Transaction
    @Query("SELECT * FROM albums")
    fun getAllAlbumsWithPhotos(): Flow<List<AlbumWithPhotos>>

    @Query("SELECT COUNT(*) == 0 FROM albums")
    suspend fun isTableEmpty(): Boolean
}