package com.example.infiniteimagesapp.data.repository.impl

import com.example.infiniteimagesapp.data.local.AlbumDao
import com.example.infiniteimagesapp.data.local.PhotoDao
import com.example.infiniteimagesapp.data.remote.PhotoAlbumsApiService
import com.example.infiniteimagesapp.data.repository.repo.PhotoAlbumsRepository
import com.example.infiniteimagesapp.data.repository.utils.Response
import com.example.infiniteimagesapp.domain.album.AlbumDTO
import com.example.infiniteimagesapp.domain.photo.PhotoDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class PhotoAlbumsRepositoryImpl(
    val photosApiService: PhotoAlbumsApiService,
    private val albumDao: AlbumDao, private val photoDao: PhotoDao
) : PhotoAlbumsRepository {
    override suspend fun getAlbumsAsync(): Flow<Response<List<AlbumDTO>>> = flow {
        emit(Response.Loading())
        try {
            val response = photosApiService.getAlbums()
            emit(Response.Success(response))

        } catch (e: HttpException) {
            emit(Response.Error(message = "Oops, something went wrong"))
        } catch (e: IOException) {
            emit(Response.Error(message = "No Network Connection"))
        }
    }

    override suspend fun getPhotosByIdAsync(albumId: Int): Flow<Response<List<PhotoDTO>>> = flow {
        emit(Response.Loading())
        try {
            val response = photosApiService.getImagesByAlbumId(albumId)
            emit(Response.Success(response))
        } catch (e: HttpException) {
            emit(Response.Error(message = "Oops, something went wrong"))
        } catch (e: IOException) {
            emit(Response.Error(message = "No Network Connection"))
        }
    }
}