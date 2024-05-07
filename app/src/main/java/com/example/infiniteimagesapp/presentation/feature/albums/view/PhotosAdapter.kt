package com.example.infiniteimagesapp.presentation.feature.albums.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.infiniteimagesapp.R
import com.example.infiniteimagesapp.databinding.RvItemPhotoBinding
import com.example.infiniteimagesapp.domain.entities.Photo

class PhotosAdapter(private val photos: MutableList<Photo>) :
RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvItemPhotoBinding.inflate(inflater, parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = photos[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = photos.size -1

    fun addAllItems(items: List<Photo>) {
        photos.clear()
        photos.addAll(items)
//        notifyItemRangeInserted(photos.size - items.size, items.size)
        notifyItemRangeInserted(photos.size - items.size, items.size)
    }

    fun removeAllItems() {
        photos.clear()
        notifyItemRangeRemoved(0, photos.size);
    }

    inner class PhotoViewHolder(private val binding: RvItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            Glide.with(binding.root)
                .load(photo.url)
                .placeholder(R.drawable.ic_broken_image)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC)) // Optional: Set disk cache strategy
                .thumbnail(Glide.with(binding.ivPhoto).load(photo.thumbnailUrl)) // Load full-size image as placeholder
                .into(binding.ivPhoto)
        }
    }
}