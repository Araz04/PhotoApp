package com.example.infiniteimagesapp.data.repository.impl

import com.example.infiniteimagesapp.data.local.AlbumDao
import com.example.infiniteimagesapp.data.local.PhotoDao
import com.example.infiniteimagesapp.data.repository.repo.LocalDatabaseRepository
import com.example.infiniteimagesapp.domain.entities.Album
import com.example.infiniteimagesapp.domain.entities.Photo
import com.example.infiniteimagesapp.domain.modles.AlbumWithPhotos
import kotlinx.coroutines.flow.Flow

class LocalDatabaseRepositoryImpl(private val albumDao: AlbumDao, private val photoDao: PhotoDao): LocalDatabaseRepository {
    override fun getAllAlbums(): Flow<List<Album>> {
        return albumDao.getAllAlbums()
    }

    override fun getAllAlbumsWithPhotos(): Flow<List<AlbumWithPhotos>> {
        return albumDao.getAllAlbumsWithPhotos()
    }

    override suspend fun insertAllAlbums(albums: List<Album>) {
        albumDao.insertAlbums(albums)
    }

    override suspend fun deleteAlbum(album: Album) {
        albumDao.deleteAlbum(album)
    }

    override suspend fun deleteAllAlbums() {
        albumDao.deleteAllAlbums()
    }

    override suspend fun insertAllPhotos(photos: List<Photo>) {
        photoDao.insertAllPhotos(photos)
    }

    override fun getPhotosByAlbumId(albumId: Int): Flow<List<Photo>> {
       return photoDao.getPhotosByAlbumId(albumId)
    }

    override suspend fun deleteAllPhotos() {
        photoDao.deleteAllPhotos()
    }
}