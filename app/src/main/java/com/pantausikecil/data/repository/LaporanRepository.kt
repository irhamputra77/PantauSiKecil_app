package com.pantausikecil.data.repository

import com.pantausikecil.network.CreateReportRequest
import com.pantausikecil.network.CreateReportResponse
import com.pantausikecil.network.PantauApiService

class LaporanRepository(private val api: PantauApiService) {
    suspend fun createReport(posyanduId: String): ApiResult<CreateReportResponse> = safeApiCall {
        api.createReport(CreateReportRequest(posyanduId = posyanduId))
    }
}
