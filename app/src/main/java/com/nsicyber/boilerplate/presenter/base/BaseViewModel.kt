package com.nsicyber.boilerplate.presenter.base

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel


open class BaseViewModel : ViewModel() {


    private var _isBusy = mutableStateOf(false)
    val isBusy: State<Boolean> = _isBusy

    val takeCount = 20

    var listener: (() -> Unit)? = null

    private var _isDialogBusy = mutableStateOf(false)
    val isDialogBusy: State<Boolean> = _isDialogBusy

    private var _successDialogState = mutableStateOf(false)
    val successDialogState: State<Boolean> = _successDialogState

    private var _errorDialogState = mutableStateOf(false)
    val errorDialogState: State<Boolean> = _errorDialogState

    var previewDialogState = mutableStateOf(false)

    private var _previewDialogType = mutableStateOf<String?>(null)
    val previewDialogType: State<String?> = _previewDialogType

    private var _previewDialogUri = mutableStateOf<String?>(null)
    val previewDialogUri: State<String?> = _previewDialogUri

    private var _errorDialogContent = mutableStateOf("")
    val errorDialogContent: State<String> = _errorDialogContent

    private var _successDialogContent = mutableStateOf("")
    val successDialogContent: State<String> = _successDialogContent

    fun setBusy(value: Boolean) {
        _isBusy.value = value;
    }

    private fun setDialogBusy(value: Boolean) {
        _isDialogBusy.value = value;
    }

    fun setSuccessDialogState(
        value: Boolean,
        body: String? = null,
        callBack: (() -> Unit)? = null
    ) {
        _successDialogState.value = value
        _successDialogContent.value = body ?: ""
        listener = callBack
    }

    fun setErrorDialogState(value: Boolean, body: String? = null) {
        _errorDialogState.value = value
        _errorDialogContent.value = body ?: ""
    }





    fun onSuccessCallback() {
        listener?.let { it() }
        listener = null
    }

    fun onActivityResult(activity: Activity) {
        val resultIntent = Intent()
        resultIntent.putExtra("some_key", "String data")
        activity.setResult(Activity.RESULT_OK, resultIntent);
        activity.finish();

    }

    fun onDismissRequest() {
        previewDialogState.value = false
        _previewDialogType.value = null
    }


}