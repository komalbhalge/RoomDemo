package com.kb.choco.domain.feature.authentication

import com.kb.choco.data.model.LoginRequest
import com.kb.choco.data.model.LoginResponse
import com.kb.choco.domain.common.BaseUseCase
import com.kb.choco.network.AuthenticationApi
import com.kb.choco.util.extensions.flowSingle
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val api: AuthenticationApi
) : BaseUseCase<LoginUseCase.Params, LoginResponse>() {

    override fun onBuild(params: Params): Flow<LoginResponse> {
        return flowSingle { api.login(params.body) }
    }

    data class Params(
        val body: LoginRequest
    )
}