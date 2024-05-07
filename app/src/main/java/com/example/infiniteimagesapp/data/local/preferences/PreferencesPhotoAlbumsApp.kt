package com.example.infiniteimagesapp.data.local.preferences

import android.content.Context
import android.content.SharedPreferences

class PreferencesPhotoAlbumsApp {
    companion object {
        private val PREF_NAME = "photo_app_shared"
        private var PRIVATE_MODE = 0

        private val PREF_IS_FIRST = "is_first_time"

        private var instance: PreferencesPhotoAlbumsApp = PreferencesPhotoAlbumsApp()
        private lateinit var sharedPreferences: SharedPreferences

        fun getInstance(context: Context): PreferencesPhotoAlbumsApp {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
            return instance
        }
    }
}