package com.pantausikecil.network

import com.google.gson.annotations.SerializedName

data class HealthResponse(
    val ok: Boolean
)

data class ErrorResponse(
    val message: String? = null,
    val error: String? = null,
    val detail: String? = null
)

// Auth

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val user: UserDto
)

data class UserDto(
    val userId: String,
    val posyanduId: String,
    val nama: String,
    val email: String,
    val role: String,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

// Anak

data class ChildDto(
    val anakId: String,
    val posyanduId: String? = null,
    val nama: String,
    val nik: String? = null,
    val jenisKelamin: String,
    val tempatLahir: String? = null,
    val tanggalLahir: String,
    val alamatAnak: String? = null,
    val rtAnak: String? = null,
    val rwAnak: String? = null,
    val kelurahan: String? = null,
    val kecamatan: String? = null,
    val kabupatenKota: String? = null,
    val namaOrangTua: String? = null,
    val nomorOrangTua: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val statusTerbaru: String? = null,
    val pemeriksaan: List<MeasurementDto>? = null
)

data class CreateChildRequest(
    val nama: String,
    val nik: String,
    val jenisKelamin: String,
    val tempatLahir: String? = null,
    val tanggalLahir: String,
    val alamatAnak: String? = null,
    val rtAnak: String? = null,
    val rwAnak: String? = null,
    val kelurahan: String,
    val kecamatan: String? = null,
    val kabupatenKota: String? = null,
    val namaOrangTua: String? = null,
    val nomorOrangTua: String? = null
)

data class UpdateChildRequest(
    val nama: String? = null,
    val nik: String? = null,
    val jenisKelamin: String? = null,
    val tanggalLahir: String? = null,
    val kelurahan: String? = null,
    val alamatAnak: String? = null,
    val rtAnak: String? = null,
    val rwAnak: String? = null,
    val nomorOrangTua: String? = null
)

// Pemeriksaan

data class MeasurementDto(
    val pemeriksaanId: String,
    val anakId: String,
    val umurTahun: Int? = null,
    val umurBulan: Int? = null,
    val tanggalPemeriksaan: String,
    val tinggiCm: String,
    val beratKg: String,
    val lingkarKepalaCm: String,
    val lingkarLenganAtasCm: String? = null,
    val caraUkur: String? = null,
    val klasifikasiStunting: String? = null,
    val saranGizi: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

data class CreateMeasurementRequest(
    val dob: String,
    val sex: String,
    val tanggalPemeriksaan: String,
    val tinggiCm: Double,
    val beratKg: Double,
    val lingkarKepalaCm: Double,
    val caraUkur: String? = null,
    val lingkarLenganAtasCm: Double? = null
)

data class MeasurementCountResponse(
    val jumlahPemeriksaan: Int
)

// Recap

data class RecapResponse(
    val totalAnak: Int,
    val normal: Int,
    val stunting: Int,
    @SerializedName("beresiko") val beresiko: Int
)

// Jadwal

data class ScheduleDto(
    val jadwalId: String,
    val posyanduId: String? = null,
    val judul: String,
    val kegiatan: String? = null,
    val scheduledAt: String,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

data class CreateScheduleRequest(
    val judul: String,
    val kegiatan: String? = null,
    val scheduledAt: String
)

data class UpdateScheduleRequest(
    val judul: String? = null,
    val kegiatan: String? = null,
    val scheduledAt: String? = null
)

data class MessageResponse(
    val message: String
)

// Admin Users

data class CreateUserRequest(
    val nama: String,
    val email: String,
    val password: String,
    val role: String = "kader"
)

data class UpdateUserRequest(
    val nama: String? = null,
    val role: String? = null,
    val password: String? = null
)

data class DeleteUserResponse(
    val message: String,
    val user: UserDto? = null
)

// Laporan

data class CreateReportRequest(
    val posyanduId: String
)

data class CreateReportResponse(
    val message: String,
    val fileName: String
)
