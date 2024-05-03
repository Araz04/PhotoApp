package com.example.infiniteimagesapp.presentation.feature.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.infiniteimagesapp.R
import com.example.infiniteimagesapp.common.base.BaseFragment
import com.example.infiniteimagesapp.common.extention.showErrorDialog
import com.example.infiniteimagesapp.databinding.FragmentAlbumsBinding
import com.example.infiniteimagesapp.presentation.feature.albums.view.AlbumsAdapter
import com.google.android.material.snackbar.Snackbar
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

    private fun initView() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            if (isInternetAvailable(requireContext())) {
                showSwipeRefresh()
                mViewModel.getAlbums()
            } else {
                Snackbar.make(
                    binding.swipeRefreshLayout,
                    getString(R.string.no_network_connection),
                    Snackbar.LENGTH_LONG
                ).show()
                hideSwipeRefresh()
            }
        }
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
            mViewModel.getAlbumsWithPhotos().collect() { albumsWithPhotosList ->
                if (albumsWithPhotosList.isNotEmpty()) {
                   hideNoDataText()
                    albumsAdapter.addAllItems(albumsWithPhotosList)
                } else {
                    showNoDataText()
                }
            }
        }

        lifecycleScope.launch {
            mViewModel.isLoading.collect { isLoading ->
                binding.swipeRefreshLayout.isRefreshing = isLoading
            }
        }

        lifecycleScope.launch {
            mViewModel.errorMessage.collect { errorMessage ->
                if (errorMessage.isNotBlank()) {
                    showErrorDialog(errorMessage)
                }
            }
        }

        lifecycleScope.launch {
            mViewModel.isTableEmpty.collect{isTableEmpty ->
                if (isTableEmpty){
                    showNoDataText()
                }else{
                    hideNoDataText()
                }
            }
        }
    }

    private fun showSwipeRefresh() {
        if (!binding.swipeRefreshLayout.isRefreshing) {
            binding.swipeRefreshLayout.isRefreshing = true
        }
    }

    private fun hideSwipeRefresh() {
        if (binding.swipeRefreshLayout.isRefreshing) {
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun showNoDataText(){
        binding.tvMessage.visibility = View.VISIBLE
        binding.rvAlbums.visibility = View.GONE
    }

    private fun hideNoDataText(){
        binding.tvMessage.visibility = View.GONE
        binding.rvAlbums.visibility = View.VISIBLE
    }
}