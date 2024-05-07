package com.example.infiniteimagesapp.presentation.feature.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
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
        initMenu()
        initAlbumAdapter()
        initData()
    }

    private fun initMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.album_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_refresh -> {
                        refreshAlbums()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun refreshAlbums() {
        if (isInternetAvailable(requireContext())) {
            showSwipeRefresh()
            mViewModel.getAlbums()
        } else {
            Snackbar.make(
                binding.rvAlbums,
                getString(R.string.no_network_connection),
                Snackbar.LENGTH_LONG
            ).show()
            hideSwipeRefresh()
        }
    }

    private fun initAlbumAdapter() {
        albumsAdapter = AlbumsAdapter(mutableListOf())
        binding.rvAlbums.adapter = albumsAdapter
        binding.rvAlbums.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = albumsAdapter
            hasFixedSize()
        }
    }

    private fun initData() {
        lifecycleScope.launch {

            mViewModel.getAlbumsWithPhotos().collect() { albumsWithPhotosList ->
                if (albumsWithPhotosList.isNotEmpty()) {
                    albumsAdapter.addAllItems(albumsWithPhotosList)
                    hideNoDataText()
                } else {
                    showNoDataText()
                }
            }
        }

        lifecycleScope.launch {
            mViewModel.isLoading.collect { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
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
            mViewModel.isTableEmpty.collect { isTableEmpty ->
                if (isTableEmpty) {
                    showNoDataText()
                } else {
                    hideNoDataText()
                }
            }
        }
    }

    private fun showSwipeRefresh() {
        if (!binding.progressBar.isVisible) {
            binding.progressBar.visibility = View.VISIBLE
        }
    }

    private fun hideSwipeRefresh() {
        if (binding.progressBar.isVisible) {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showNoDataText() {
        binding.tvMessage.visibility = View.VISIBLE
        binding.rvAlbums.visibility = View.GONE
    }

    private fun hideNoDataText() {
        binding.tvMessage.visibility = View.GONE
        binding.rvAlbums.visibility = View.VISIBLE
    }
}