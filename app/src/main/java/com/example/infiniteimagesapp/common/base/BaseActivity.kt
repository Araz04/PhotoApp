package com.example.infiniteimagesapp.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.infiniteimagesapp.common.extention.setupProgressDialog

abstract class BaseActivity: AppCompatActivity() {
    abstract fun getViewModel(): BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupProgressDialog(this, getViewModel().progressBar)
    }
}