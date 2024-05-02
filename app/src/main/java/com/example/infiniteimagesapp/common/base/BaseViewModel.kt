package com.example.infiniteimagesapp.common.base

import androidx.lifecycle.ViewModel
import com.example.infiniteimagesapp.common.util.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

open class BaseViewModel : ViewModel()  {
    private val _progressBar = MutableStateFlow(Event(false))
    val progressBar: Flow<Event<Boolean>> get() = _progressBar

    fun setProgressBarVisibility(visible: Boolean) {
        _progressBar.value = Event(visible)
    }

    private val _dialogError = MutableStateFlow(Event(0))
    val dialogError: Flow<Event<Int>> get() = _dialogError

    private val _dialogErrorString = MutableStateFlow(Event(""))
    val dialogErrorString: Flow<Event<String>> get() = _dialogErrorString

    // FOR BACK HANDLER
    private val _backEvent = MutableStateFlow<Event<Boolean>>(Event(false))
    val backEvent: Flow<Event<Boolean>> get() = _backEvent

    private val _unauthorizedEvent = MutableStateFlow<Event<Boolean>>(Event(false))
    val unauthorizedEvent: Flow<Event<Boolean>> get() = _unauthorizedEvent

    fun showProgressBar() {
        _progressBar.value = Event(true)
    }

    fun showProgressBarActivity() {
        _progressBar.value = Event(true)
    }

    fun hideProgressBarActivity() {
        _progressBar.value = Event(false)
    }

    fun hideProgressBar() {
        _progressBar.value = Event(false)
    }

    fun showErrorDialog(res: Int) {
        _dialogError.value = Event(res)
    }

    fun showErrorDialog(error: String) {
        _dialogErrorString.value = Event(error)
    }

    fun showAppErrorDialog(res: Int) {
        _dialogError.value = Event(res)
    }

    fun showAppErrorDialog(error: String) {
        _dialogErrorString.value = Event(error)
    }

    fun showAppErrorDialogActivity(res: Int) {
        _dialogError.value = Event(res)
    }

    fun showAppErrorDialogActivity(error: String) {
        _dialogErrorString.value = Event(error)
    }

    fun onBackPress() {
        _backEvent.value = Event(true)
    }

    fun isUnauthorized() {
        _unauthorizedEvent.value = Event(true)
    }
}