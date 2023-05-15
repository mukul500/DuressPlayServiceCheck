package net.xblacky.duressplayservicecheck.ui.theme

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel(){

    val _statusCode: MutableStateFlow<Int> = MutableStateFlow(-1)
    val statusCode: StateFlow<Int> = _statusCode
    val _errorMessage: MutableStateFlow<String> = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage


    fun updateStatus(statusCode: Int, errorMessage: String) {
        this._statusCode.value = statusCode
        this._errorMessage.value = errorMessage
    }
}