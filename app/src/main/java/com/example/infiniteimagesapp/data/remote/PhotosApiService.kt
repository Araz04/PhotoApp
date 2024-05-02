package com.example.infiniteimagesapp.data.remote

import com.example.infiniteimagesapp.domain.album.AlbumDTO
import com.example.infiniteimagesapp.domain.photo.PhotoDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotosApiService {
    @GET("/albums")
    suspend fun getAlbums(
    ): List<AlbumDTO>

    @GET("/photos")
    suspend fun getImagesByAlbumId(
        @Query("albumId") albumId: Int
    ): List<PhotoDTO>
}