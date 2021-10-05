package com.kb.choco.util.extensions

import android.util.Log
import com.kb.choco.ui.common.UIController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.IOException
import kotlin.coroutines.Continuation
import kotlin.coroutines.resumeWithException

fun <T> Flow<T>.launchWith(
    controller: UIController,
    scope: CoroutineScope = controller,
    continuation: Continuation<Unit>? = null,
    onError: ((Throwable) -> Unit)? = null
) = this
    .retryWhen { cause, _ ->
        if (cause is IOException) {
            showNoInternetAlert(controller)
            true
        } else {
            false
        }
    }.catch {

        continuation?.resumeWithException(it)

        onError?.let { _ ->
            onError.invoke(it)
        } ?: run {
            it.message?.let { it1 -> Log.e("Error", it1) }
        }
    }
    .launchIn(scope)

fun <T> flowSingle(action: suspend () -> T): Flow<T> {
    return flow { emit(action.invoke()) }
}

private suspend fun showNoInternetAlert(uiController: UIController) =
    suspendCancellableCoroutine<Unit> { continuation ->
        uiController.showNoConnectionError()
    }
