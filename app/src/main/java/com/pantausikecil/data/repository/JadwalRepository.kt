package com.pantausikecil.data.repository

import com.pantausikecil.network.CreateScheduleRequest
import com.pantausikecil.network.PantauApiService
import com.pantausikecil.network.ScheduleDto
import com.pantausikecil.network.UpdateScheduleRequest

class JadwalRepository(private val api: PantauApiService) {
    suspend fun getSchedules(from: String? = null, to: String? = null, query: String? = null): ApiResult<List<ScheduleDto>> = safeApiCall {
        api.getSchedules(from = from, to = to, query = query)
    }

    suspend fun createSchedule(request: CreateScheduleRequest): ApiResult<ScheduleDto> = safeApiCall {
        api.createSchedule(request)
    }

    suspend fun updateSchedule(jadwalId: String, request: UpdateScheduleRequest): ApiResult<ScheduleDto> = safeApiCall {
        api.updateSchedule(jadwalId, request)
    }

    suspend fun deleteSchedule(jadwalId: String): ApiResult<Unit> = safeApiCall {
        api.deleteSchedule(jadwalId)
        Unit
    }

    suspend fun triggerSchedule(jadwalId: String): ApiResult<String> = safeApiCall {
        api.triggerSchedule(jadwalId).message
    }
}
