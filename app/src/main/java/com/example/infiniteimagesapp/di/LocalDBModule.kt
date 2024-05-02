package com.example.infiniteimagesapp.di

import android.content.Context
import androidx.room.Room
import com.example.infiniteimagesapp.data.local.AlbumDao
import com.example.infiniteimagesapp.data.local.PhotoDatabase
import com.example.infiniteimagesapp.data.repository.impl.LocalDatabaseRepositoryImpl
import com.example.infiniteimagesapp.data.repository.repo.LocalDatabaseRepository
import org.koin.dsl.module

val createLocalDBModule = module {
    single { provideRoomDatabase(get()) }
    single { provideAlbumDao(get()) }
    single { providePhotoDao(get()) }
    single<LocalDatabaseRepository> { LocalDatabaseRepositoryImpl(get<PhotoDatabase>().albumDao, get<PhotoDatabase>().photoDao) }
}

fun provideRoomDatabase(context: Context): PhotoDatabase =
    Room.databaseBuilder(
        context = context,
        klass = PhotoDatabase::class.java,
        name = PhotoDatabase.DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .build()

fun provideAlbumDao(photoDatabase: PhotoDatabase): AlbumDao = photoDatabase.albumDao
fun providePhotoDao(photoDatabase: PhotoDatabase): AlbumDao = photoDatabase.albumDao