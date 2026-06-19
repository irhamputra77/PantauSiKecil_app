package com.pantausikecil.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Streaming

interface ChildrenApiService {

    @GET("anak")
    suspend fun getChildren(
        @Header("Authorization") authorization: String
    ): List<ChildResponseDto>

    @GET("anak/{anakId}/jumlah")
    suspend fun getExaminationCount(
        @Header("Authorization") authorization: String,
        @Path("anakId") anakId: String
    ): ExaminationCountResponseDto

    @POST("anak")
    suspend fun createChild(
        @Header("Authorization") authorization: String,
        @Body body: ChildRequestDto
    ): ChildResponseDto

    @PATCH("anak/{id}")
    suspend fun updateChild(
        @Header("Authorization") authorization: String,
        @Path("id") id: String,
        @Body body: ChildRequestDto
    ): ChildResponseDto

    @DELETE("anak/{id}")
    suspend fun deleteChild(
        @Header("Authorization") authorization: String,
        @Path("id") id: String
    ): DeleteResponseDto

    @POST("laporan")
    suspend fun generateReport(
        @Header("Authorization") authorization: String,
        @Body body: GenerateReportRequestDto
    ): GenerateReportResponseDto

    @Streaming
    @GET("laporan/download/{filename}")
    suspend fun downloadReport(
        @Path("filename") filename: String
    ): Response<ResponseBody>
}