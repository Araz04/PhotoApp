package com.example.infiniteimagesapp.presentation

import android.app.Application
import com.example.infiniteimagesapp.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PhotosApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@PhotosApplication)
            modules(
                appComponent
            )
        }
    }
}