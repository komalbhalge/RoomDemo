package com.kb.choco.ui.common

import android.content.Context
import kotlinx.coroutines.CoroutineScope

interface UIController : CoroutineScope {
    fun showNoConnectionError()
    fun showPopup(
        context: Context,
        title: String? = null,
        message: String? = null,
        buttonLabel: String? = null,
        heroImage: Int? = null,
        onDismiss: Boolean? = null,
        errorCode: String? = null
    )
}