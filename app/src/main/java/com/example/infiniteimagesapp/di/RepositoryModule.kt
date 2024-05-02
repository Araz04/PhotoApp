package com.example.infiniteimagesapp.di

import com.example.infiniteimagesapp.data.local.PhotoDatabase
import com.example.infiniteimagesapp.data.repository.impl.PhotosRepositoryImpl
import com.example.infiniteimagesapp.data.repository.repo.PhotosRepository
import org.koin.dsl.module

val repositoryModule = module {
        single<PhotosRepository> { PhotosRepositoryImpl(get(), get<PhotoDatabase>().albumDao,  get<PhotoDatabase>().photoDao) }
}