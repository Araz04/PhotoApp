package com.example.infiniteimagesapp.di

import com.example.infiniteimagesapp.data.local.preferences.PreferencesPhotoAlbumsApp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single { PreferencesPhotoAlbumsApp.getInstance(androidContext()) }
}