package com.kb.choco.ui.common

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kb.choco.R
import com.kb.choco.ui.common.UIController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel(), UIController {

    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext

    val shouldShowNoInternetAlert = MutableLiveData<Boolean>()
    val isError = MutableLiveData<Boolean>()

    override fun showPopup(
        context: Context,
        title: String?,
        message: String?,
        buttonLabel: String?,
        heroImage: Int?,
        onDismiss: Boolean?,
        errorCode: String?
    ) {
        val builder = AlertDialog.Builder(context)
            .setIcon(R.drawable.ic_error)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(
                R.string.okay
            ) { dialogInterface, i -> dialogInterface.cancel() }
        val dialog: AlertDialog? = builder.create()
        dialog?.show()
        dialog?.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(Color.parseColor("#FF0B8B42"));
    }

    override fun showNoConnectionError() {
        shouldShowNoInternetAlert.value = true
    }

}