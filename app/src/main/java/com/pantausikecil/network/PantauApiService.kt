package com.pantausikecil.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming

interface PantauApiService {
    @GET("health")
    suspend fun health(): HealthResponse

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("anak")
    suspend fun getChildren(): List<ChildDto>

    @POST("anak")
    suspend fun createChild(@Body request: CreateChildRequest): ChildDto

    @GET("anak/{id}")
    suspend fun getChildDetail(@Path("id") anakId: String): ChildDto

    @PATCH("anak/{id}")
    suspend fun updateChild(
        @Path("id") anakId: String,
        @Body request: UpdateChildRequest
    ): ChildDto

    @DELETE("anak/{id}")
    suspend fun deleteChild(@Path("id") anakId: String): MessageResponse

    @POST("anak/{anakId}/pemeriksaan")
    suspend fun createMeasurement(
        @Path("anakId") anakId: String,
        @Body request: CreateMeasurementRequest
    ): MeasurementDto

    @GET("anak/{anakId}/pemeriksaan")
    suspend fun getMeasurements(@Path("anakId") anakId: String): List<MeasurementDto>

    @GET("anak/{anakId}/jumlah")
    suspend fun getMeasurementCount(@Path("anakId") anakId: String): MeasurementCountResponse

    @GET("pemeriksaan/{id}")
    suspend fun getMeasurementDetail(@Path("id") pemeriksaanId: String): MeasurementDto

    @DELETE("pemeriksaan/{id}")
    suspend fun deleteMeasurement(@Path("id") pemeriksaanId: String): MessageResponse

    @GET("recap")
    suspend fun getRecap(): RecapResponse

    @GET("admin/users")
    suspend fun getUsers(): List<UserDto>

    @POST("admin/users")
    suspend fun createUser(@Body request: CreateUserRequest): UserDto

    @GET("admin/users/{id}")
    suspend fun getUser(@Path("id") userId: String): UserDto

    @PATCH("admin/users/{id}")
    suspend fun updateUser(
        @Path("id") userId: String,
        @Body request: UpdateUserRequest
    ): UserDto

    @DELETE("admin/users/{id}")
    suspend fun deleteUser(@Path("id") userId: String): DeleteUserResponse

    @GET("jadwal")
    suspend fun getSchedules(
        @Query("from") from: String? = null,
        @Query("to") to: String? = null,
        @Query("q") query: String? = null
    ): List<ScheduleDto>

    @GET("jadwal/{id}")
    suspend fun getSchedule(@Path("id") jadwalId: String): ScheduleDto

    @POST("jadwal")
    suspend fun createSchedule(@Body request: CreateScheduleRequest): ScheduleDto

    @PUT("jadwal/{id}")
    suspend fun updateSchedule(
        @Path("id") jadwalId: String,
        @Body request: UpdateScheduleRequest
    ): ScheduleDto

    @DELETE("jadwal/{id}")
    suspend fun deleteSchedule(@Path("id") jadwalId: String): MessageResponse

    @POST("jadwal/{id}/trigger")
    suspend fun triggerSchedule(@Path("id") jadwalId: String): MessageResponse

    @POST("laporan")
    suspend fun createReport(@Body request: CreateReportRequest): CreateReportResponse

    @Streaming
    @GET("laporan/download/{filename}")
    suspend fun downloadReport(@Path("filename") filename: String): Response<ResponseBody>
}
