package com.example.infiniteimagesapp.presentation.feature.albums

import androidx.lifecycle.viewModelScope
import com.example.infiniteimagesapp.common.base.BaseViewModel
import com.example.infiniteimagesapp.data.repository.repo.LocalDatabaseRepository
import com.example.infiniteimagesapp.data.repository.repo.PhotoAlbumsRepository
import com.example.infiniteimagesapp.data.repository.utils.Response
import com.example.infiniteimagesapp.domain.entities.Album
import com.example.infiniteimagesapp.domain.mapper.toAlbums
import com.example.infiniteimagesapp.domain.mapper.toPhotos
import com.example.infiniteimagesapp.domain.modles.AlbumWithPhotos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AlbumsViewModel(
    private val photoAlbumsRepository: PhotoAlbumsRepository,
    private val localDbRepository: LocalDatabaseRepository
) : BaseViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _isTableEmpty = MutableStateFlow(false)
    val isTableEmpty: StateFlow<Boolean> get() = _isTableEmpty

    private fun getAllSavedAlbums(): Flow<List<Album>> {
        return localDbRepository.getAllAlbums()
    }

    fun getAlbumsWithPhotos(): Flow<List<AlbumWithPhotos>> {
        return localDbRepository.getAllAlbumsWithPhotos()
    }

    init {
        getAlbums()
        viewModelScope.launch {
            val result = localDbRepository.isAlbumsEmpty()
            _isTableEmpty.value = result
        }
    }

    fun getAlbums() {
        viewModelScope.launch(Dispatchers.IO) {
            photoAlbumsRepository.getAlbumsAsync().collectLatest { res ->
                when (res) {
                    is Response.Loading -> {
                        _isLoading.value = true
                    }

                    is Response.Error -> {
                        _errorMessage.value = res.message ?: "An unknown error occurred"
                        _isLoading.value = false
                    }

                    is Response.Success -> {
                        if (res.data == null) {
                            _errorMessage.value =
                                res.message ?: "No network connection, but you can see saved albums"
                        } else {
                            val data = res.data.toAlbums()
                            localDbRepository.insertAllAlbums(data)
                            getAllSavedAlbums().collect() { albums ->
                                albums.forEach { album ->
                                    getPhotosById(album.id)
                                }
                            }
                        }
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    private fun getPhotosById(albumId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            photoAlbumsRepository.getPhotosByIdAsync(albumId).collectLatest { res ->
                when (res) {
                    is Response.Loading -> {
                        _isLoading.value = true
                    }

                    is Response.Error -> {
                        _errorMessage.value = res.message ?: "An unknown error occurred"
                        _isLoading.value = false
                    }

                    is Response.Success -> {
                        if (res.data == null) {
                            _errorMessage.value =
                                res.message ?: "No network connection, but you can see saved photos"
                        } else {
                            val data = res.data.toPhotos()
                            localDbRepository.insertAllPhotos(data)
                        }
                        _isLoading.value = false
                    }
                }
            }
        }
    }
}