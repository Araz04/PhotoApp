package com.example.infiniteimagesapp.data.repository.repo

import com.example.infiniteimagesapp.data.repository.utils.Response
import com.example.infiniteimagesapp.domain.album.AlbumDTO
import com.example.infiniteimagesapp.domain.photo.PhotoDTO
import kotlinx.coroutines.flow.Flow

interface PhotoAlbumsRepository {
    suspend fun getAlbumsAsync(): Flow<Response<List<AlbumDTO>>>
    suspend fun getPhotosByIdAsync(albumId: Int): Flow<Response<List<PhotoDTO>>>
}