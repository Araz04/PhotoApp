package com.example.infiniteimagesapp.data.repository.impl

import com.example.infiniteimagesapp.data.local.AlbumDao
import com.example.infiniteimagesapp.data.local.PhotoDao
import com.example.infiniteimagesapp.data.repository.repo.LocalDatabaseRepository
import com.example.infiniteimagesapp.domain.mapper.Album
import com.example.infiniteimagesapp.domain.mapper.Photo
import kotlinx.coroutines.flow.Flow

class LocalDatabaseRepositoryImpl(private val albumDao: AlbumDao, private val photoDao: PhotoDao): LocalDatabaseRepository {
    override fun getAllAlbums(): Flow<List<Album>> {
        return albumDao.getAllAlbums()
    }

    override suspend fun insertAlbum(album: Album) {
        albumDao.insert(album)
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

    override suspend fun insertAPhoto(photo: Photo) {
        photoDao.insert(photo)
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

    override suspend fun deletePhotosByAlbumId(albumId: Int) {
        photoDao.deletePhotosByAlbumId(albumId)
    }
}