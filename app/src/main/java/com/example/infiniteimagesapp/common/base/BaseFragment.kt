package com.example.infiniteimagesapp.common.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

abstract class BaseFragment: Fragment(), DefaultLifecycleObserver {
        abstract fun getViewModel(): BaseViewModel

        override fun onResume(owner: LifecycleOwner) {
            super<DefaultLifecycleObserver>.onResume(owner)
            onCreated()
        }

        private fun onCreated(){
        }

        override fun onAttach(context: Context) {
            super.onAttach(context)
            lifecycle.addObserver(this)
        }

        override fun onDetach() {
            super.onDetach()
            lifecycle.removeObserver(this)
        }

    fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
        return result
    }
}