package com.example.infiniteimagesapp.presentation.feature.albums

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.infiniteimagesapp.common.base.BaseViewModel
import com.example.infiniteimagesapp.data.repository.repo.LocalDatabaseRepository
import com.example.infiniteimagesapp.data.repository.repo.PhotosRepository
import com.example.infiniteimagesapp.data.repository.utils.Response
import com.example.infiniteimagesapp.domain.entities.Album
import com.example.infiniteimagesapp.domain.entities.Photo
import com.example.infiniteimagesapp.domain.mapper.toAlbums
import com.example.infiniteimagesapp.domain.mapper.toPhotos
import com.example.infiniteimagesapp.domain.modles.AlbumWithPhotos
import com.example.infiniteimagesapp.presentation.ui.states.AlbumState
import com.example.infiniteimagesapp.presentation.ui.states.State
import com.example.infiniteimagesapp.presentation.ui.states.PhotoState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AlbumsViewModel(private val photosRepository: PhotosRepository, private val localDbRepository: LocalDatabaseRepository): BaseViewModel() {
    val albums = MutableStateFlow(AlbumState())
    val photos = MutableStateFlow(PhotoState())
    val state = MutableStateFlow(State())

    fun getAllSavedAlbums(): Flow<List<Album>> {
        return localDbRepository.getAllAlbums()
    }

    fun getSavedPhotos(albumId: Int): Flow<List<Photo>>{
        return localDbRepository.getPhotosByAlbumId(albumId)
    }

    fun getAlbumsWithPhotos(): Flow<List<AlbumWithPhotos>>{
        return localDbRepository.getAllAlbumsWithPhotos()
    }

    init {
        getAlbums()
    }

    fun getAlbums() {
        viewModelScope.launch(Dispatchers.IO) {
            photosRepository.getAlbumsAsync().collectLatest { res ->
                when (res) {
                    is Response.Loading -> state.update {
                        it.copy(
                            isLoading = true,
                            error = ""
                        )
                    }
                    is Response.Error -> state.update {
                        Log.e("ViewModel albums", res.message.toString())
                        it.copy(
                            isLoading = false,
                            error = res.message ?: "An unknown error occurred"
                        )
                    }
                    is Response.Success -> {
                        if (res.data == null) {
                            state.update {
                                it.copy(
                                    isLoading = false,
                                    error = res.message ?: "No network connection, but you can see saved albums"
                                )
                            }
                        } else {
                            val data = res.data.toAlbums()
                            localDbRepository.insertAllAlbums(data)
                            getAllSavedAlbums().collect(){albums ->
                                albums.forEach {album ->
                                    getPhotosById(album.id)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getPhotosById(albumId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            photosRepository.getPhotosByIdAsync(albumId).collectLatest { res ->
                when (res) {
                    is Response.Loading -> state.update {
                        it.copy(
                            isLoading = true,
                            error = ""
                        )
                    }
                    is Response.Error -> state.update {
                        Log.e("ViewModel albums", res.message.toString())
                        it.copy(
                            isLoading = false,
                            error = res.message ?: "An unknown error occurred"
                        )
                    }
                    is Response.Success -> {
                        if (res.data == null) {
                            state.update {
                                it.copy(
                                    isLoading = false,
                                    error = res.message ?: "No network connection, but you can see saved photos"
                                )
                            }
                        } else {
                            val data = res.data.toPhotos()
                            localDbRepository.insertAllPhotos(data)
                        }
                    }
                }
            }
        }
    }
}