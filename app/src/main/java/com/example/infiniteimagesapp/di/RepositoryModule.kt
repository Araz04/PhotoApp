package com.example.infiniteimagesapp.di

import com.example.infiniteimagesapp.data.local.PhotoDatabase
import com.example.infiniteimagesapp.data.repository.impl.PhotoAlbumsRepositoryImpl
import com.example.infiniteimagesapp.data.repository.repo.PhotoAlbumsRepository
import org.koin.dsl.module

val repositoryModule = module {
        single<PhotoAlbumsRepository> { PhotoAlbumsRepositoryImpl(get(), get<PhotoDatabase>().albumDao,  get<PhotoDatabase>().photoDao) }
}