package com.pantausikecil.data.repository

import com.pantausikecil.network.PantauApiService
import com.pantausikecil.network.RecapResponse

class RecapRepository(private val api: PantauApiService) {
    suspend fun getRecap(): ApiResult<RecapResponse> = safeApiCall { api.getRecap() }
}
