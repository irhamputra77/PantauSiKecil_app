package com.pantausikecil.data.repository

import com.pantausikecil.data.remote.ChildRequestDto
import com.pantausikecil.data.remote.ChildrenApiService
import com.pantausikecil.data.remote.GenerateReportRequestDto
import com.pantausikecil.data.remote.toUiModel
import com.pantausikecil.ui.screens.dashboard.DataAnakUiModel

class ChildrenRepository(
    private val api: ChildrenApiService
) {
    suspend fun getChildren(
        token: String
    ): List<DataAnakUiModel> {
        val authHeader = "Bearer $token"

        val children = api.getChildren(authHeader)

        return children.map { child ->
            val count = try {
                api.getExaminationCount(
                    authorization = authHeader,
                    anakId = child.anakId
                ).jumlahPemeriksaan
            } catch (_: Exception) {
                0
            }

            child.toUiModel(
                jumlahPemeriksaan = count
            )
        }
    }

    suspend fun createChild(
        token: String,
        request: ChildRequestDto
    ) {
        api.createChild(
            authorization = "Bearer $token",
            body = request
        )
    }

    suspend fun updateChild(
        token: String,
        childId: String,
        request: ChildRequestDto
    ) {
        api.updateChild(
            authorization = "Bearer $token",
            id = childId,
            body = request
        )
    }

    suspend fun deleteChild(
        token: String,
        childId: String
    ) {
        api.deleteChild(
            authorization = "Bearer $token",
            id = childId
        )
    }

    suspend fun generateReport(
        token: String,
        posyanduId: String
    ): String {
        val response = api.generateReport(
            authorization = "Bearer $token",
            body = GenerateReportRequestDto(
                posyanduId = posyanduId
            )
        )

        return response.fileName
    }
}