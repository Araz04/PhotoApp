package com.example.infiniteimagesapp.di

import com.example.infiniteimagesapp.presentation.ui.preferences.PreferencesPhotoApp
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single { PreferencesPhotoApp.getInstance(androidContext()) }
}