package com.example.infiniteimagesapp.presentation.ui.preferences

import android.content.Context
import android.content.SharedPreferences

class PreferencesPhotoApp {
    companion object {
        private val PREF_NAME = "photo_app_shared"
        private var PRIVATE_MODE = 0

        private val PREF_IS_FIRST = "is_first_time"
        private val PREF_VERTICAL_LIST_POSITION = "vertical_list_position"
        private val PREF_HORIZONTA_LIST_POSITION = "horizontal_list_position"

        private var instance: PreferencesPhotoApp = PreferencesPhotoApp()
        private lateinit var sharedPreferences: SharedPreferences

        fun getInstance(context: Context): PreferencesPhotoApp {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
            return instance
        }
    }

    fun getVerticalListPosition(): Int {
        return sharedPreferences.getInt(PREF_VERTICAL_LIST_POSITION, -1)
    }

    fun setVerticalListPosition(position: Int) {
        sharedPreferences.edit().putInt(PREF_VERTICAL_LIST_POSITION, position).apply()
    }

    fun getHorizontalListPosition(): Int? {
        return sharedPreferences.getInt(PREF_HORIZONTA_LIST_POSITION, -1)
    }

    fun setHorizontalListPosition(position: Int) {
        sharedPreferences.edit().putInt(PREF_HORIZONTA_LIST_POSITION, position).apply()
    }
}