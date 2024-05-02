package com.example.infiniteimagesapp.presentation.ui.states

import com.example.infiniteimagesapp.domain.mapper.Photo

data class PhotoState(val photos:List<Photo> = emptyList(),
                      val isLoading:Boolean = true,
                      val error:String = "")