package com.pantausikecil.model

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

data class UserSession(
    val email: String,
    val name: String = email,
    val role: UserRole = UserRole.Kader,
    val token: String? = null,
    val posyanduId: String? = null
)

enum class UserRole(val label: String) {
    Kader("Kader"),
    OrangTua("Orang Tua"),
    TenagaKesehatan("Tenaga Kesehatan"),
    Admin("Admin")
}

enum class Gender(val label: String, val apiValue: String) {
    Male("Laki - Laki", "Laki - Laki"),
    Female("Perempuan", "Perempuan")
}

enum class GrowthStatus(val label: String) {
    Normal("Normal"),
    Risk("Beresiko"),
    Stunting("Stunting"),
    GiziKurang("Gizi Kurang"),
    GiziCukup("Gizi Cukup")
}

data class Measurement(
    val id: String,
    val date: String,
    val heightCm: Double,
    val weightKg: Double,
    val headCircumferenceCm: Double,
    val armCircumferenceCm: Double,
    val measureMethod: String = "Berdiri",
    val status: GrowthStatus = GrowthStatus.Normal,
    val nutritionAdvice: String = ""
)

data class Child(
    val id: String,
    val name: String,
    val parentName: String,
    val phone: String,
    val address: String,
    val birthPlace: String,
    val birthDate: String,
    val ageLabel: String,
    val gender: Gender,
    val status: GrowthStatus,
    val birthDateApi: String = birthDate,
    val nik: String = "",
    val kelurahan: String = "",
    val kecamatan: String = "",
    val kabupatenKota: String = "",
    val rt: String = "",
    val rw: String = "",
    val measurements: List<Measurement> = emptyList(),
    val examinationCount: Int = measurements.size
)

data class ChildFormInput(
    val nama: String,
    val nik: String,
    val jenisKelamin: Gender,
    val tempatLahir: String,
    val tanggalLahir: String,
    val namaOrangTua: String,
    val nomorOrangTua: String,
    val alamatAnak: String,
    val rtAnak: String,
    val rwAnak: String,
    val kelurahan: String,
    val kecamatan: String,
    val kabupatenKota: String
)

data class PosyanduSchedule(
    val id: String,
    val title: String,
    val activity: String,
    val date: String,
    val time: String,
    val scheduledAtIso: String = ""
)

fun classifyGrowth(heightCm: Double, weightKg: Double): GrowthStatus {
    return when {
        heightCm < 80.0 -> GrowthStatus.Stunting
        weightKg < 10.0 -> GrowthStatus.GiziKurang
        heightCm < 90.0 -> GrowthStatus.Risk
        else -> GrowthStatus.Normal
    }
}

fun parseApiRole(role: String?): UserRole {
    return when (role?.lowercase(Locale.getDefault())) {
        "admin" -> UserRole.Admin
        "kader" -> UserRole.Kader
        else -> UserRole.Kader
    }
}

fun parseApiGender(value: String?): Gender {
    return when (value?.uppercase(Locale.getDefault())) {
        "P", "F", "FEMALE", "PEREMPUAN" -> Gender.Female
        else -> Gender.Male
    }
}

fun parseApiStatus(value: String?): GrowthStatus {
    val text = value.orEmpty().lowercase(Locale.getDefault())
    return when {
        text.contains("stunting berat") -> GrowthStatus.Stunting
        text == "stunting" || text.contains("stunting") && !text.contains("resiko") && !text.contains("risiko") -> GrowthStatus.Stunting
        text.contains("resiko") || text.contains("risiko") || text.contains("below -1") -> GrowthStatus.Risk
        text.contains("gizi kurang") -> GrowthStatus.GiziKurang
        text.contains("gizi cukup") -> GrowthStatus.GiziCukup
        else -> GrowthStatus.Normal
    }
}

fun toApiDate(input: String): String {
    // Expected backend date body: yyyy-MM-dd. UI may already type this manually.
    return input.trim().ifBlank { "2022-01-01" }
}

fun nowIso(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    return formatter.format(Date())
}
