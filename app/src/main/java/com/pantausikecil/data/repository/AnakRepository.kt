package com.pantausikecil.data.repository

import com.pantausikecil.network.ChildDto
import com.pantausikecil.network.CreateChildRequest
import com.pantausikecil.network.MeasurementCountResponse
import com.pantausikecil.network.PantauApiService
import com.pantausikecil.network.UpdateChildRequest

class AnakRepository(
    private val api: PantauApiService
) {
    suspend fun getChildren(): ApiResult<List<ChildDto>> = safeApiCall {
        api.getChildren()
    }

    suspend fun createChild(
        request: CreateChildRequest
    ): ApiResult<ChildDto> = safeApiCall {
        api.createChild(request)
    }

    suspend fun getChildDetail(
        anakId: String
    ): ApiResult<ChildDto> = safeApiCall {
        api.getChildDetail(anakId)
    }

    suspend fun updateChild(
        anakId: String,
        request: UpdateChildRequest
    ): ApiResult<ChildDto> = safeApiCall {
        api.updateChild(anakId, request)
    }

    suspend fun deleteChild(
        anakId: String
    ): ApiResult<Unit> = safeApiCall {
        api.deleteChild(anakId)
        Unit
    }

    suspend fun getMeasurementCount(
        anakId: String
    ): ApiResult<MeasurementCountResponse> = safeApiCall {
        api.getMeasurementCount(anakId)
    }
}
