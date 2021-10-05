package com.kb.choco.network

import com.kb.choco.data.model.LoginRequest
import com.kb.choco.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationApi {

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse
}
