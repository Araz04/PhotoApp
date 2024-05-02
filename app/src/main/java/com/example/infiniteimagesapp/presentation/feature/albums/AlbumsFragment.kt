package com.example.infiniteimagesapp.presentation.feature.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.infiniteimagesapp.common.base.BaseFragment
import com.example.infiniteimagesapp.databinding.FragmentAlbumsBinding
import com.example.infiniteimagesapp.domain.mapper.Album
import com.example.infiniteimagesapp.domain.mapper.AlbumWithPhotos
import com.example.infiniteimagesapp.presentation.feature.albums.view.AlbumsAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumsFragment : BaseFragment() {
    private val mViewModel: AlbumsViewModel by viewModel()
    private var _binding: FragmentAlbumsBinding? = null
    private val binding get() = _binding!!

    override fun getViewModel() = mViewModel

    private lateinit var albumsAdapter: AlbumsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlbumsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAlbumAdapter()
        initData()
    }

    private fun initView(){
        binding.swipeRefreshLayout.setOnRefreshListener {
            showSwipeRefresh()
            mViewModel.getAlbums()
        }
        showSwipeRefresh()
    }

    private fun initAlbumAdapter() {
        albumsAdapter = AlbumsAdapter(mutableListOf())
        binding.rvAlbums.adapter = albumsAdapter
        binding.rvAlbums.apply {
            setOrientation(RecyclerView.VERTICAL)
            setInfinite(true)
            setAlpha(false)
            setFlat(true)
            setIsScrollingEnabled(true)
            hasFixedSize()
        }
    }

    private fun initData() {
        lifecycleScope.launch {
            mViewModel.getAllSavedAlbums().collect() { albums ->
                    fetchAlbumsWithPhotos(albums)
            }
        }
    }

    private fun fetchAlbumsWithPhotos(albums: List<Album>) {
        lifecycleScope.launch {
            val albumsWithPhotosList = mutableListOf<AlbumWithPhotos>()
            albums.forEach { album ->
                launch {
                    mViewModel.getSavedPhotos(album.id).collect { photos ->
                        val albumWithPhotos = AlbumWithPhotos(album, photos.toMutableList())
                        albumsWithPhotosList.add(albumWithPhotos)
                        // Check if we have collected photos for all albums
                        if (albumsWithPhotosList.size == albums.size) {
                            // Update the UI once all photos are collected
                            val sortedItems = albumsWithPhotosList.sortedBy { it.album.id }
                            albumsAdapter.addAllItems(sortedItems)
                        }
                    }
                }
            }
            hideSwipeRefresh()

        }
    }

    private fun showSwipeRefresh(){
        if (!binding.swipeRefreshLayout.isRefreshing){
            binding.swipeRefreshLayout.isRefreshing = true
        }
    }

    private fun hideSwipeRefresh(){
        if (binding.swipeRefreshLayout.isRefreshing){
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }
}