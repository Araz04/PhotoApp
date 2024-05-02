package com.example.infiniteimagesapp.data.repository.repo

import com.example.infiniteimagesapp.domain.entities.Album
import com.example.infiniteimagesapp.domain.entities.Photo
import com.example.infiniteimagesapp.domain.modles.AlbumWithPhotos
import kotlinx.coroutines.flow.Flow

interface LocalDatabaseRepository {
    fun getAllAlbums(): Flow<List<Album>>
    fun getAllAlbumsWithPhotos(): Flow<List<AlbumWithPhotos>>

    suspend fun insertAllAlbums(albums: List<Album>)

    fun getPhotosByAlbumId(albumId: Int): Flow<List<Photo>>

    suspend fun deleteAlbum(album: Album)

    suspend fun deleteAllAlbums()

    suspend fun insertAllPhotos(albums: List<Photo>)

    suspend fun deleteAllPhotos()
}