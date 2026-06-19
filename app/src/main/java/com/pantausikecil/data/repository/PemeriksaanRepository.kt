package com.pantausikecil.data.repository

import com.pantausikecil.network.CreateMeasurementRequest
import com.pantausikecil.network.MeasurementDto
import com.pantausikecil.network.PantauApiService

class PemeriksaanRepository(private val api: PantauApiService) {
    suspend fun createMeasurement(anakId: String, request: CreateMeasurementRequest): ApiResult<MeasurementDto> = safeApiCall {
        api.createMeasurement(anakId, request)
    }

    suspend fun getMeasurements(anakId: String): ApiResult<List<MeasurementDto>> = safeApiCall {
        api.getMeasurements(anakId)
    }

    suspend fun getMeasurementDetail(pemeriksaanId: String): ApiResult<MeasurementDto> = safeApiCall {
        api.getMeasurementDetail(pemeriksaanId)
    }

    suspend fun deleteMeasurement(pemeriksaanId: String): ApiResult<Unit> = safeApiCall {
        api.deleteMeasurement(pemeriksaanId)
        Unit
    }
}
