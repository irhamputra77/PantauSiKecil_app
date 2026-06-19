package com.pantausikecil.ui.screens.dashboard

enum class DataAnakStatus {
    Normal,
    Beresiko,
    Stunting
}

enum class DataAnakGiziStatus {
    GiziCukup,
    GiziKurang
}

data class DataAnakUiModel(
    val id: String,
    val nama: String,
    val namaOrangTua: String,
    val umurBulan: Int,
    val jenisKelamin: String,
    val statusTerbaru: DataAnakStatus,
    val statusGizi: DataAnakGiziStatus,
    val jumlahPemeriksaan: Int
)