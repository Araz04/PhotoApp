package com.example.infiniteimagesapp.presentation.ui.preferences

import android.content.Context
import android.content.SharedPreferences

class PreferencesPhotoApp {
    companion object {
        private val PREF_NAME = "photo_app_shared"
        private var PRIVATE_MODE = 0

        private val PREF_IS_FIRST = "is_first_time"

        private var instance: PreferencesPhotoApp = PreferencesPhotoApp()
        private lateinit var sharedPreferences: SharedPreferences

        fun getInstance(context: Context): PreferencesPhotoApp {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
            return instance
        }
    }
}