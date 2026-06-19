package com.pantausikecil.data

import com.pantausikecil.model.Child
import com.pantausikecil.model.Gender
import com.pantausikecil.model.GrowthStatus
import com.pantausikecil.model.Measurement
import com.pantausikecil.model.PosyanduSchedule

object MockData {
    fun children(): List<Child> {
        return listOf(
            Child(
                id = "child-1",
                name = "Aqua",
                parentName = "Ibu Rina",
                phone = "081234567890",
                address = "Jl. Melati No. 12",
                birthPlace = "Bandung",
                birthDate = "13 Januari 2022",
                ageLabel = "4 Tahun",
                gender = Gender.Male,
                status = GrowthStatus.Normal,
                measurements = listOf(
                    Measurement(
                        id = "m-1",
                        date = "13 Januari 2026",
                        heightCm = 135.5,
                        weightKg = 30.0,
                        headCircumferenceCm = 30.0,
                        armCircumferenceCm = 10.0,
                        status = GrowthStatus.Normal
                    ),
                    Measurement(
                        id = "m-2",
                        date = "13 Desember 2025",
                        heightCm = 131.0,
                        weightKg = 28.0,
                        headCircumferenceCm = 29.0,
                        armCircumferenceCm = 10.0,
                        status = GrowthStatus.Normal
                    )
                )
            ),
            Child(
                id = "child-2",
                name = "Ruby",
                parentName = "Ibu Sari",
                phone = "082222111333",
                address = "Kp. Mawar RT 02 RW 03",
                birthPlace = "Bandung",
                birthDate = "04 Maret 2024",
                ageLabel = "15 Bulan",
                gender = Gender.Female,
                status = GrowthStatus.Risk,
                measurements = listOf(
                    Measurement(
                        id = "m-3",
                        date = "13 Januari 2026",
                        heightCm = 82.0,
                        weightKg = 10.5,
                        headCircumferenceCm = 28.0,
                        armCircumferenceCm = 8.0,
                        status = GrowthStatus.Risk
                    )
                )
            ),
            Child(
                id = "child-3",
                name = "Jakip",
                parentName = "Ibu Nia",
                phone = "083456789123",
                address = "Desa Sukamaju",
                birthPlace = "Garut",
                birthDate = "20 Juni 2023",
                ageLabel = "31 Bulan",
                gender = Gender.Male,
                status = GrowthStatus.Stunting,
                measurements = listOf(
                    Measurement(
                        id = "m-4",
                        date = "13 Januari 2026",
                        heightCm = 79.0,
                        weightKg = 9.0,
                        headCircumferenceCm = 27.0,
                        armCircumferenceCm = 7.0,
                        status = GrowthStatus.Stunting
                    )
                )
            )
        )
    }

    fun schedules(): List<PosyanduSchedule> {
        return listOf(
            PosyanduSchedule(
                id = "schedule-1",
                title = "Jadwal Posyandu Januari",
                activity = "Pemeriksaan rutin balita",
                date = "13 Januari 2026",
                time = "08.00"
            ),
            PosyanduSchedule(
                id = "schedule-2",
                title = "Jadwal Posyandu Imunisasi",
                activity = "Pemeriksaan rutin balita",
                date = "20 Januari 2026",
                time = "09.00"
            ),
            PosyanduSchedule(
                id = "schedule-3",
                title = "Jadwal Posyandu Februari",
                activity = "Pemeriksaan rutin balita",
                date = "10 Februari 2026",
                time = "08.30"
            ),
            PosyanduSchedule(
                id = "schedule-4",
                title = "Jadwal Posyandu Susulan",
                activity = "Pemeriksaan rutin balita",
                date = "17 Februari 2026",
                time = "09.30"
            )
        )
    }
}
