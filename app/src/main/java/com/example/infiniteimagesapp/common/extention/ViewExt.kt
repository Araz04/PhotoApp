package com.example.infiniteimagesapp.common.extention

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.infiniteimagesapp.R
import com.example.infiniteimagesapp.common.util.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

lateinit var dialog: AlertDialog

fun Fragment.showErrorDialog(errorText: String) {
    AlertDialog.Builder(context)
        .setMessage(errorText)
        .setPositiveButton(getString(R.string.ok)) { _, _ ->
        }
        .show()
}

fun Fragment.showProgressDialog() {
    activity?.let {
        if (::dialog.isInitialized && !(context as Activity).isFinishing && !dialog.isShowing) {
            dialog.show()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}

fun Fragment.hideProgressDialog() {
    activity?.let {
        try {
            if (::dialog.isInitialized && !(context as Activity).isFinishing && dialog.isShowing) dialog.dismiss()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun Activity.setupProgressDialog(
    lifecycleOwner: LifecycleOwner,
    progressDialogEvent: Flow<Event<Boolean>>
) {
    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
    builder.setCancelable(false)
    builder.setView(R.layout.loading_dialog)
    dialog = builder.create()
    lifecycleOwner.lifecycleScope.launch {
        progressDialogEvent.collect{event ->
            event.getContentIfNotHandled()?.let { isShow ->
                this.let {
                    if (isShow){
                        showProgressDialog()
                    }else{
                        hideProgressDialog()
                    }
                }
            }
        }
    }
}

fun Activity.showProgressDialog() {
    let {
        if (::dialog.isInitialized && !this.isFinishing && !dialog.isShowing) {
            dialog.show()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

    }
}

fun Activity.hideProgressDialog() {
    let {
        try {
            if (::dialog.isInitialized && !this.isFinishing && dialog.isShowing) dialog.dismiss()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
