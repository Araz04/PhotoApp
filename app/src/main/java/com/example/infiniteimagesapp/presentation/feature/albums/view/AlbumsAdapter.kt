package com.example.infiniteimagesapp.presentation.feature.albums.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.infiniteimagesapp.R
import com.example.infiniteimagesapp.databinding.RvItemAlbumBinding
import com.example.infiniteimagesapp.domain.modles.AlbumWithPhotos

class AlbumsAdapter(private val albums: MutableList<AlbumWithPhotos>) :
RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvItemAlbumBinding.inflate(inflater, parent, false)
        return AlbumsViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {
        val currentItem = albums[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = albums.size

    fun addAllItems(items: List<AlbumWithPhotos>) {
        albums.clear()
        albums.addAll(items)
        notifyItemRangeInserted(albums.size - items.size, items.size)
    }

    fun removeAllItems() {
        albums.clear()
        notifyItemRangeRemoved(0, albums.size);
    }

    inner class AlbumsViewHolder(private val binding: RvItemAlbumBinding, val context: Context, ) : RecyclerView.ViewHolder(binding.root) {
        private val photosAdapter = PhotosAdapter(mutableListOf())
        init {
            binding.rvPhotos.apply {
                adapter = photosAdapter
                hasFixedSize()
                setOrientation(RecyclerView.HORIZONTAL)
                setInfinite(true)
                setAlpha(false)
                setFlat(true)
                setIsScrollingEnabled(true)
            }
        }

        fun bind(albumWithPhotos: AlbumWithPhotos) {
            binding.tvAlbumTitle.text = context.getString(
                R.string.text_album_name,
                albumWithPhotos.album.id,
                albumWithPhotos.album.title
            )
            if (albumWithPhotos.photos.isNotEmpty()){
                photosAdapter.addAllItems(albumWithPhotos.photos)
            }
        }
    }
}