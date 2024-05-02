package com.example.infiniteimagesapp.common.base

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.infiniteimagesapp.common.extention.setupProgressDialog

abstract class BaseFragment: Fragment(), DefaultLifecycleObserver {
        abstract fun getViewModel(): BaseViewModel

        override fun onResume(owner: LifecycleOwner) {
            super<DefaultLifecycleObserver>.onResume(owner)
            onCreated()
        }

        private fun onCreated(){
            setupProgressDialog(this, getViewModel().progressBar)
        }

        override fun onAttach(context: Context) {
            super.onAttach(context)
            lifecycle.addObserver(this)
        }

        override fun onDetach() {
            super.onDetach()
            lifecycle.removeObserver(this)
        }
}