package com.pantausikecil.network

import com.pantausikecil.model.Child
import com.pantausikecil.model.ChildFormInput
import com.pantausikecil.model.Gender
import com.pantausikecil.model.GrowthStatus
import com.pantausikecil.model.Measurement
import com.pantausikecil.model.PosyanduSchedule
import com.pantausikecil.model.classifyGrowth
import com.pantausikecil.model.parseApiGender
import com.pantausikecil.model.parseApiStatus
import com.pantausikecil.model.toApiDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun ChildDto.toUiChild(): Child {
    val genderModel = parseApiGender(jenisKelamin)
    val latestStatus = parseApiStatus(statusTerbaru ?: pemeriksaan?.firstOrNull()?.klasifikasiStunting)
    val measurementList = pemeriksaan.orEmpty().map { it.toUiMeasurement() }
    val addressParts = listOfNotNull(
        alamatAnak?.takeIf { it.isNotBlank() },
        rtAnak?.takeIf { it.isNotBlank() }?.let { "RT $it" },
        rwAnak?.takeIf { it.isNotBlank() }?.let { "RW $it" },
        kelurahan?.takeIf { it.isNotBlank() },
        kecamatan?.takeIf { it.isNotBlank() },
        kabupatenKota?.takeIf { it.isNotBlank() }
    )

    return Child(
        id = anakId,
        name = nama,
        parentName = namaOrangTua.orEmpty().ifBlank { "-" },
        phone = nomorOrangTua.orEmpty().ifBlank { "-" },
        address = addressParts.joinToString(", ").ifBlank { "-" },
        birthPlace = tempatLahir.orEmpty().ifBlank { "-" },
        birthDate = tanggalLahir.toReadableDate(),
        birthDateApi = tanggalLahir.take(10),
        ageLabel = pemeriksaan?.firstOrNull()?.umurBulan?.let { "$it bulan" } ?: "-",
        gender = genderModel,
        status = latestStatus,
        nik = nik.orEmpty(),
        kelurahan = kelurahan.orEmpty(),
        kecamatan = kecamatan.orEmpty(),
        kabupatenKota = kabupatenKota.orEmpty(),
        rt = rtAnak.orEmpty(),
        rw = rwAnak.orEmpty(),
        measurements = measurementList
    )
}

fun MeasurementDto.toUiMeasurement(): Measurement {
    val height = tinggiCm.toDoubleOrNull() ?: 0.0
    val weight = beratKg.toDoubleOrNull() ?: 0.0
    val status = parseApiStatus(klasifikasiStunting)
        .takeUnless { klasifikasiStunting.isNullOrBlank() }
        ?: classifyGrowth(height, weight)

    return Measurement(
        id = pemeriksaanId,
        date = tanggalPemeriksaan.toReadableDate(),
        heightCm = height,
        weightKg = weight,
        headCircumferenceCm = lingkarKepalaCm.toDoubleOrNull() ?: 0.0,
        armCircumferenceCm = lingkarLenganAtasCm?.toDoubleOrNull() ?: 0.0,
        measureMethod = caraUkur.orEmpty().ifBlank { "Berdiri" },
        status = status,
        nutritionAdvice = saranGizi.orEmpty()
    )
}

fun ScheduleDto.toUiSchedule(): PosyanduSchedule {
    val parsed = parseIso(scheduledAt)
    return PosyanduSchedule(
        id = jadwalId,
        title = judul,
        activity = kegiatan.orEmpty().ifBlank { "Kegiatan Posyandu" },
        date = parsed?.format(DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("id", "ID"))) ?: scheduledAt,
        time = parsed?.format(DateTimeFormatter.ofPattern("HH:mm", Locale("id", "ID"))) ?: "-",
        scheduledAtIso = scheduledAt
    )
}

fun ChildFormInput.toCreateChildRequest(): CreateChildRequest {
    return CreateChildRequest(
        nama = nama,
        nik = nik,
        jenisKelamin = jenisKelamin.apiValue,
        tempatLahir = tempatLahir.ifBlank { null },
        tanggalLahir = toApiDate(tanggalLahir),
        alamatAnak = alamatAnak.ifBlank { null },
        rtAnak = rtAnak.ifBlank { null },
        rwAnak = rwAnak.ifBlank { null },
        kelurahan = kelurahan,
        kecamatan = kecamatan.ifBlank { null },
        kabupatenKota = kabupatenKota.ifBlank { null },
        namaOrangTua = namaOrangTua.ifBlank { null },
        nomorOrangTua = nomorOrangTua.ifBlank { null }
    )
}

fun ChildFormInput.toUiChild(id: String = "child-${System.currentTimeMillis()}"): Child {
    val addressParts = listOfNotNull(
        alamatAnak.takeIf { it.isNotBlank() },
        rtAnak.takeIf { it.isNotBlank() }?.let { "RT $it" },
        rwAnak.takeIf { it.isNotBlank() }?.let { "RW $it" },
        kelurahan.takeIf { it.isNotBlank() },
        kecamatan.takeIf { it.isNotBlank() },
        kabupatenKota.takeIf { it.isNotBlank() }
    )

    return Child(
        id = id,
        name = nama.ifBlank { "Anak Baru" },
        parentName = namaOrangTua.ifBlank { "-" },
        phone = nomorOrangTua.ifBlank { "-" },
        address = addressParts.joinToString(", ").ifBlank { "-" },
        birthPlace = tempatLahir.ifBlank { "-" },
        birthDate = tanggalLahir.ifBlank { "-" },
        birthDateApi = tanggalLahir.ifBlank { "2022-01-01" }.take(10),
        ageLabel = "Belum ada pemeriksaan",
        gender = jenisKelamin,
        status = GrowthStatus.Normal,
        nik = nik,
        kelurahan = kelurahan,
        kecamatan = kecamatan,
        kabupatenKota = kabupatenKota,
        rt = rtAnak,
        rw = rwAnak
    )
}

fun PosyanduSchedule.toCreateScheduleRequest(): CreateScheduleRequest {
    return CreateScheduleRequest(
        judul = title,
        kegiatan = activity.ifBlank { null },
        scheduledAt = scheduledAtIso.ifBlank { composeScheduleIso(date, time) }
    )
}

fun PosyanduSchedule.toUpdateScheduleRequest(): UpdateScheduleRequest {
    return UpdateScheduleRequest(
        judul = title,
        kegiatan = activity.ifBlank { null },
        scheduledAt = scheduledAtIso.ifBlank { composeScheduleIso(date, time) }
    )
}

fun Measurement.toCreateMeasurementRequest(child: Child): CreateMeasurementRequest {
    return CreateMeasurementRequest(
        dob = toApiDate(child.birthDateApi),
        sex = child.gender.apiValue,
        tanggalPemeriksaan = date.toIsoDateTime(),
        tinggiCm = heightCm,
        beratKg = weightKg,
        lingkarKepalaCm = headCircumferenceCm,
        caraUkur = measureMethod,
        lingkarLenganAtasCm = armCircumferenceCm.takeIf { it > 0.0 }
    )
}

private fun String.toReadableDate(): String {
    val parsed = parseIso(this)
    return parsed?.format(DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("id", "ID"))) ?: this.take(10)
}

private fun String.toIsoDateTime(): String {
    if (contains("T")) return this
    val safe = trim()
    val apiDateRegex = Regex("\\d{4}-\\d{2}-\\d{2}")
    val date = if (apiDateRegex.matches(safe.take(10))) safe.take(10) else java.time.LocalDate.now().toString()
    return "${date}T08:00:00.000Z"
}

private fun composeScheduleIso(date: String, time: String): String {
    val dateRegex = Regex("\\d{4}-\\d{2}-\\d{2}")
    val rawDate = date.trim()
    val safeDate = if (dateRegex.matches(rawDate.take(10))) rawDate.take(10) else java.time.LocalDate.now().toString()
    val normalizedTime = time.trim().replace('.', ':').ifBlank { "08:00" }
    val safeTime = if (Regex("\\d{2}:\\d{2}").matches(normalizedTime.take(5))) normalizedTime.take(5) else "08:00"
    return "${safeDate}T${safeTime}:00.000Z"
}

private fun parseIso(value: String?): OffsetDateTime? {
    return try {
        if (value.isNullOrBlank()) null else OffsetDateTime.parse(value)
    } catch (_: Exception) {
        null
    }
}
