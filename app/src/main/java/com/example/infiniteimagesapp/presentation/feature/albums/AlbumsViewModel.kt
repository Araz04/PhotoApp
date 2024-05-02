package com.example.infiniteimagesapp.presentation.feature.albums

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.infiniteimagesapp.common.base.BaseViewModel
import com.example.infiniteimagesapp.data.repository.repo.LocalDatabaseRepository
import com.example.infiniteimagesapp.data.repository.repo.PhotosRepository
import com.example.infiniteimagesapp.data.repository.utils.Response
import com.example.infiniteimagesapp.domain.mapper.Album
import com.example.infiniteimagesapp.domain.mapper.Photo
import com.example.infiniteimagesapp.domain.mapper.toAlbums
import com.example.infiniteimagesapp.domain.mapper.toPhotos
import com.example.infiniteimagesapp.presentation.ui.states.AlbumState
import com.example.infiniteimagesapp.presentation.ui.states.PhotoState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AlbumsViewModel(private val photosRepo: PhotosRepository, private val localDbRepository: LocalDatabaseRepository): BaseViewModel() {
    val albums = MutableStateFlow(AlbumState())
    val photos = MutableStateFlow(PhotoState())

    fun getAllSavedAlbums(): Flow<List<Album>> {
        return localDbRepository.getAllAlbums()
    }

    fun getSavedPhotos(albumId: Int): Flow<List<Photo>>{
        return localDbRepository.getPhotosByAlbumId(albumId)
    }

    init {
        getAlbums()
    }

    fun getAlbums() {
        viewModelScope.launch(Dispatchers.IO) {
            photosRepo.getAlbumsAsync().collectLatest { res ->
                when (res) {
                    is Response.Loading -> albums.update {
                        it.copy(
                            isLoading = true,
                            error = ""
                        )
                    }
                    is Response.Error -> albums.update {
                        Log.e("ViewModel albums", res.message.toString())
                        it.copy(
                            isLoading = false,
                            error = res.message ?: "An unknown error occurred"
                        )
                    }
                    is Response.Success -> {
                        if (res.data == null) {
                            albums.update {
                                it.copy(
                                    isLoading = false,
                                    error = res.message ?: "Sorry can't fetch albums right now."
                                )
                            }
                        } else {
                            val data = res.data.toAlbums()
//                            localDbRepository.insertAllAlbums(data)
                            data.forEach {
                                localDbRepository.insertAlbum(it)
                            }
                            getAllSavedAlbums().collect(){albums ->
                                albums.forEach {album ->
                                    getPhotosById(album.id)
                                }
                            }

                            albums.update {
                                it.copy(
                                    albums = data,
                                    isLoading = false,
                                    error = ""
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun getPhotosById(albumId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            photosRepo.getPhotosByIdAsync(albumId).collectLatest { res ->
                when (res) {
                    is Response.Loading -> photos.update {
                        it.copy(
                            isLoading = true,
                            error = ""
                        )
                    }
                    is Response.Error -> photos.update {
                        Log.e("ViewModel albums", res.message.toString())
                        it.copy(
                            isLoading = false,
                            error = res.message ?: "An unknown error occurred"
                        )
                    }
                    is Response.Success -> {
                        if (res.data == null) {
                            photos.update {
                                it.copy(
                                    isLoading = false,
                                    error = res.message ?: "Sorry can't fetch photos right now."
                                )
                            }
                        } else {
                            val data = res.data.toPhotos()
                            localDbRepository.insertAllPhotos(data)
                            photos.update {
                                it.copy(
                                    photos = data,
                                    isLoading = false,
                                    error = ""
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}