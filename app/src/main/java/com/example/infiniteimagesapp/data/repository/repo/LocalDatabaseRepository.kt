package com.example.infiniteimagesapp.data.repository.repo

import com.example.infiniteimagesapp.domain.mapper.Album
import com.example.infiniteimagesapp.domain.mapper.Photo
import kotlinx.coroutines.flow.Flow

interface LocalDatabaseRepository {
    fun getAllAlbums(): Flow<List<Album>>

    suspend fun insertAlbum(album: Album)

    suspend fun insertAllAlbums(albums: List<Album>)

    fun getPhotosByAlbumId(albumId: Int): Flow<List<Photo>>

    suspend fun deleteAlbum(album: Album)

    suspend fun deleteAllAlbums()

    suspend fun insertAPhoto(photo: Photo)

    suspend fun insertAllPhotos(albums: List<Photo>)

    suspend fun deleteAllPhotos()

    suspend fun deletePhotosByAlbumId(albumId: Int)
}