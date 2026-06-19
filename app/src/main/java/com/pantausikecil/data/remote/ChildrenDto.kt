package com.pantausikecil.data.remote

import com.google.gson.annotations.SerializedName
import com.pantausikecil.ui.screens.dashboard.DataAnakGiziStatus
import com.pantausikecil.ui.screens.dashboard.DataAnakStatus
import com.pantausikecil.ui.screens.dashboard.DataAnakUiModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class ChildResponseDto(
    @SerializedName("anakId")
    val anakId: String,

    @SerializedName("posyanduId")
    val posyanduId: String?,

    @SerializedName("nama")
    val nama: String?,

    @SerializedName("nik")
    val nik: String?,

    @SerializedName("jenisKelamin")
    val jenisKelamin: String?,

    @SerializedName("tempatLahir")
    val tempatLahir: String?,

    @SerializedName("tanggalLahir")
    val tanggalLahir: String?,

    @SerializedName("alamatAnak")
    val alamatAnak: String?,

    @SerializedName("rtAnak")
    val rtAnak: String?,

    @SerializedName("rwAnak")
    val rwAnak: String?,

    @SerializedName("kelurahan")
    val kelurahan: String?,

    @SerializedName("kecamatan")
    val kecamatan: String?,

    @SerializedName("kabupatenKota")
    val kabupatenKota: String?,

    @SerializedName("namaOrangTua")
    val namaOrangTua: String?,

    @SerializedName("nomorOrangTua")
    val nomorOrangTua: String?,

    @SerializedName("statusTerbaru")
    val statusTerbaru: String?
)

data class ChildRequestDto(
    @SerializedName("nama")
    val nama: String,

    @SerializedName("nik")
    val nik: String,

    @SerializedName("jenisKelamin")
    val jenisKelamin: String,

    @SerializedName("tempatLahir")
    val tempatLahir: String? = null,

    @SerializedName("tanggalLahir")
    val tanggalLahir: String,

    @SerializedName("alamatAnak")
    val alamatAnak: String? = null,

    @SerializedName("rtAnak")
    val rtAnak: String? = null,

    @SerializedName("rwAnak")
    val rwAnak: String? = null,

    @SerializedName("kelurahan")
    val kelurahan: String,

    @SerializedName("kecamatan")
    val kecamatan: String? = null,

    @SerializedName("kabupatenKota")
    val kabupatenKota: String? = null,

    @SerializedName("namaOrangTua")
    val namaOrangTua: String? = null,

    @SerializedName("nomorOrangTua")
    val nomorOrangTua: String? = null
)

data class ExaminationCountResponseDto(
    @SerializedName("jumlahPemeriksaan")
    val jumlahPemeriksaan: Int
)

data class DeleteResponseDto(
    @SerializedName("message")
    val message: String?
)

data class GenerateReportRequestDto(
    @SerializedName("posyanduId")
    val posyanduId: String
)

data class GenerateReportResponseDto(
    @SerializedName("message")
    val message: String?,

    @SerializedName("fileName")
    val fileName: String
)

fun ChildResponseDto.toUiModel(
    jumlahPemeriksaan: Int
): DataAnakUiModel {
    val mappedStatus = mapStatus(statusTerbaru)

    return DataAnakUiModel(
        id = anakId,
        nama = nama.orEmpty(),
        namaOrangTua = namaOrangTua.orEmpty(),
        umurBulan = calculateAgeInMonths(tanggalLahir),
        jenisKelamin = jenisKelamin.orEmpty(),
        statusTerbaru = mappedStatus,
        statusGizi = when (mappedStatus) {
            DataAnakStatus.Normal -> DataAnakGiziStatus.GiziCukup
            DataAnakStatus.Beresiko,
            DataAnakStatus.Stunting -> DataAnakGiziStatus.GiziKurang
        },
        jumlahPemeriksaan = jumlahPemeriksaan
    )
}

private fun mapStatus(value: String?): DataAnakStatus {
    val normalized = value.orEmpty().lowercase()

    return when {
        normalized.contains("stunting berat") -> DataAnakStatus.Stunting
        normalized.contains("stunting") && !normalized.contains("beresiko") -> DataAnakStatus.Stunting
        normalized.contains("beresiko") || normalized.contains("risiko") -> DataAnakStatus.Beresiko
        else -> DataAnakStatus.Normal
    }
}

private fun calculateAgeInMonths(dateString: String?): Int {
    if (dateString.isNullOrBlank()) return 0

    val patterns = listOf(
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        "yyyy-MM-dd'T'HH:mm:ss'Z'",
        "yyyy-MM-dd"
    )

    val birthDate = patterns.firstNotNullOfOrNull { pattern ->
        try {
            SimpleDateFormat(pattern, Locale.US).parse(dateString)
        } catch (_: Exception) {
            null
        }
    } ?: return 0

    val birth = Calendar.getInstance()
    birth.time = birthDate

    val now = Calendar.getInstance()

    var months = (now.get(Calendar.YEAR) - birth.get(Calendar.YEAR)) * 12
    months += now.get(Calendar.MONTH) - birth.get(Calendar.MONTH)

    if (now.get(Calendar.DAY_OF_MONTH) < birth.get(Calendar.DAY_OF_MONTH)) {
        months -= 1
    }

    return months.coerceAtLeast(0)
}