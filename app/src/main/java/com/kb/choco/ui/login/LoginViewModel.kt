package com.kb.choco.ui.login

import androidx.lifecycle.MutableLiveData
import com.kb.choco.data.model.LoginRequest
import com.kb.choco.domain.feature.authentication.LoginUseCase
import com.kb.choco.ui.common.BaseViewModel
import com.kb.choco.util.extensions.launchWith
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    val loginToken = MutableLiveData("")

    fun login(req: LoginRequest) {
        loginUseCase.build(
            LoginUseCase.Params(req)
        )
            .onEach { loginToken.postValue(it.token) }
            .launchWith(this, onError = { onError(it) })
    }

    private fun onError(t: Throwable) {
        isError.postValue(true)
    }
}