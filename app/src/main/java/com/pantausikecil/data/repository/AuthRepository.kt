package com.pantausikecil.data.repository

import com.pantausikecil.network.LoginRequest
import com.pantausikecil.network.LoginResponse
import com.pantausikecil.network.PantauApiService
import com.pantausikecil.network.TokenStore

class AuthRepository(
    private val api: PantauApiService,
    private val tokenStore: TokenStore
) {
    suspend fun login(email: String, password: String): ApiResult<LoginResponse> {
        val result = safeApiCall { api.login(LoginRequest(email = email, password = password)) }
        if (result is ApiResult.Success) {
            tokenStore.saveLogin(result.data.token, result.data.user)
        }
        return result
    }

    fun logout() {
        tokenStore.clear()
    }
}
