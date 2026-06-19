package com.pantausikecil.data.repository

import retrofit2.HttpException
import java.io.IOException

sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>
    data class Error(val message: String, val throwable: Throwable? = null) : ApiResult<Nothing>
}

suspend fun <T> safeApiCall(block: suspend () -> T): ApiResult<T> {
    return try {
        ApiResult.Success(block())
    } catch (e: HttpException) {
        ApiResult.Error("HTTP ${e.code()}: ${e.message()}", e)
    } catch (e: IOException) {
        ApiResult.Error("Tidak dapat terhubung ke server. Pastikan backend berjalan dan base URL benar.", e)
    } catch (e: Exception) {
        ApiResult.Error(e.message ?: "Terjadi kesalahan tidak diketahui", e)
    }
}
