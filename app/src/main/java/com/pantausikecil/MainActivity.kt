package com.pantausikecil

import android.os.Bundle
import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.pantausikecil.data.repository.AnakRepository
import com.pantausikecil.data.repository.ApiResult
import com.pantausikecil.data.repository.AuthRepository
import com.pantausikecil.data.repository.JadwalRepository
import com.pantausikecil.data.repository.LaporanRepository
import com.pantausikecil.data.repository.PemeriksaanRepository
import com.pantausikecil.model.Child
import com.pantausikecil.model.ChildFormInput
import com.pantausikecil.model.Measurement
import com.pantausikecil.model.PosyanduSchedule
import com.pantausikecil.model.UserSession
import com.pantausikecil.model.parseApiRole
import com.pantausikecil.network.ApiClient
import com.pantausikecil.network.ApiConfig
import com.pantausikecil.network.TokenStore
import com.pantausikecil.network.toCreateChildRequest
import com.pantausikecil.network.toCreateMeasurementRequest
import com.pantausikecil.network.toCreateScheduleRequest
import com.pantausikecil.network.toUiChild
import com.pantausikecil.network.toUiSchedule
import com.pantausikecil.network.toUpdateScheduleRequest
import com.pantausikecil.ui.screens.ChildDetailScreen
import com.pantausikecil.ui.screens.ChildFormScreen
import com.pantausikecil.ui.screens.ChildPickerScreen
import com.pantausikecil.ui.screens.LandingScreen
import com.pantausikecil.ui.screens.LoginScreen
import com.pantausikecil.ui.screens.dashboard.DashboardScreen
import com.pantausikecil.ui.theme.PantauSiKecilTheme
import kotlinx.coroutines.launch

sealed interface AppScreen {
    data object Login : AppScreen
    data object Landing : AppScreen
    data object Dashboard : AppScreen
    data object ChildForm : AppScreen
    data object PickChild : AppScreen
    data class DetailChild(val childId: String) : AppScreen
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PantauSiKecilTheme {
                val scope = rememberCoroutineScope()

                val tokenStore = remember {
                    TokenStore(applicationContext)
                }

                val api = remember {
                    ApiClient.create(tokenStore)
                }

                val authRepository = remember {
                    AuthRepository(api, tokenStore)
                }

                val anakRepository = remember {
                    AnakRepository(api)
                }

                val jadwalRepository = remember {
                    JadwalRepository(api)
                }

                val pemeriksaanRepository = remember {
                    PemeriksaanRepository(api)
                }

                val laporanRepository = remember {
                    LaporanRepository(api)
                }

                var screen by remember {
                    mutableStateOf<AppScreen>(AppScreen.Login)
                }

                var session by remember {
                    mutableStateOf<UserSession?>(null)
                }

                var loginLoading by remember {
                    mutableStateOf(false)
                }

                var loginError by remember {
                    mutableStateOf<String?>(null)
                }

                var apiMessage by remember {
                    mutableStateOf<String?>(null)
                }

                val children = remember {
                    mutableStateListOf<Child>()
                }

                val schedules = remember {
                    mutableStateListOf<PosyanduSchedule>()
                }

                fun loadDashboardData() {
                    scope.launch {
                        try {
                            when (val result = anakRepository.getChildren()) {
                                is ApiResult.Success -> {
                                    children.clear()

                                    val mappedChildren = result.data.map { childDto ->
                                        val child = childDto.toUiChild()

                                        val count = when (
                                            val countResult = anakRepository.getMeasurementCount(child.id)
                                        ) {
                                            is ApiResult.Success -> countResult.data.jumlahPemeriksaan
                                            is ApiResult.Error -> child.measurements.size
                                        }

                                        child.copy(
                                            examinationCount = count
                                        )
                                    }

                                    children.addAll(mappedChildren)
                                }

                                is ApiResult.Error -> {
                                    children.clear()
                                    apiMessage = result.message
                                }
                            }

                            when (val result = jadwalRepository.getSchedules()) {
                                is ApiResult.Success -> {
                                    schedules.clear()
                                    schedules.addAll(
                                        result.data.map { scheduleDto ->
                                            scheduleDto.toUiSchedule()
                                        }
                                    )
                                }

                                is ApiResult.Error -> {
                                    schedules.clear()
                                    apiMessage = result.message
                                }
                            }
                        } catch (e: Exception) {
                            children.clear()
                            schedules.clear()
                            apiMessage = e.message ?: "Gagal memuat data dashboard."
                        }
                    }
                }

                fun addChild(
                    input: ChildFormInput,
                    onSuccess: () -> Unit
                ) {
                    scope.launch {
                        try {
                            val request = input.toCreateChildRequest()

                            when (
                                val result = anakRepository.createChild(request)
                            ) {
                                is ApiResult.Success -> {
                                    children.add(
                                        0,
                                        result.data.toUiChild().copy(
                                            examinationCount = 0
                                        )
                                    )

                                    apiMessage = "Data anak berhasil ditambahkan."
                                    onSuccess()
                                }

                                is ApiResult.Error -> {
                                    apiMessage = result.message.ifBlank {
                                        "Gagal menambahkan data anak."
                                    }
                                }
                            }
                        } catch (e: Exception) {
                            apiMessage = e.message ?: "Gagal menambahkan data anak."
                        }
                    }
                }

                fun addSchedule(schedule: PosyanduSchedule) {
                    scope.launch {
                        try {
                            when (
                                val result = jadwalRepository.createSchedule(
                                    schedule.toCreateScheduleRequest()
                                )
                            ) {
                                is ApiResult.Success -> {
                                    schedules.add(
                                        0,
                                        result.data.toUiSchedule()
                                    )
                                }

                                is ApiResult.Error -> {
                                    apiMessage = result.message
                                }
                            }
                        } catch (e: Exception) {
                            apiMessage = e.message ?: "Gagal menambahkan jadwal."
                        }
                    }
                }

                fun updateSchedule(schedule: PosyanduSchedule) {
                    scope.launch {
                        try {
                            when (
                                val result = jadwalRepository.updateSchedule(
                                    schedule.id,
                                    schedule.toUpdateScheduleRequest()
                                )
                            ) {
                                is ApiResult.Success -> {
                                    val updatedSchedule = result.data.toUiSchedule()

                                    val index = schedules.indexOfFirst {
                                        it.id == updatedSchedule.id
                                    }

                                    if (index >= 0) {
                                        schedules[index] = updatedSchedule
                                    } else {
                                        schedules.add(0, updatedSchedule)
                                    }
                                }

                                is ApiResult.Error -> {
                                    apiMessage = result.message
                                }
                            }
                        } catch (e: Exception) {
                            apiMessage = e.message ?: "Gagal mengubah jadwal."
                        }
                    }
                }

                fun deleteSchedule(scheduleId: String) {
                    scope.launch {
                        try {
                            when (
                                val result = jadwalRepository.deleteSchedule(scheduleId)
                            ) {
                                is ApiResult.Success -> {
                                    schedules.removeAll {
                                        it.id == scheduleId
                                    }
                                }

                                is ApiResult.Error -> {
                                    apiMessage = result.message
                                }
                            }
                        } catch (e: Exception) {
                            apiMessage = e.message ?: "Gagal menghapus jadwal."
                        }
                    }
                }

                fun addMeasurement(
                    childId: String,
                    measurement: Measurement
                ) {
                    val childIndex = children.indexOfFirst {
                        it.id == childId
                    }

                    if (childIndex < 0) {
                        apiMessage = "Data anak tidak ditemukan."
                        return
                    }

                    val targetChild = children[childIndex]

                    children[childIndex] = targetChild.copy(
                        status = measurement.status,
                        measurements = listOf(measurement) + targetChild.measurements,
                        examinationCount = targetChild.examinationCount + 1
                    )

                    scope.launch {
                        try {
                            when (
                                val result = pemeriksaanRepository.createMeasurement(
                                    childId,
                                    measurement.toCreateMeasurementRequest(targetChild)
                                )
                            ) {
                                is ApiResult.Success -> {
                                    val refreshed = anakRepository.getChildDetail(childId)

                                    if (refreshed is ApiResult.Success) {
                                        val updatedIndex = children.indexOfFirst {
                                            it.id == childId
                                        }

                                        if (updatedIndex >= 0) {
                                            val refreshedChild = refreshed.data.toUiChild()

                                            val count = when (
                                                val countResult = anakRepository.getMeasurementCount(childId)
                                            ) {
                                                is ApiResult.Success -> countResult.data.jumlahPemeriksaan
                                                is ApiResult.Error -> refreshedChild.measurements.size
                                            }

                                            children[updatedIndex] = refreshedChild.copy(
                                                examinationCount = count
                                            )
                                        }
                                    }
                                }

                                is ApiResult.Error -> {
                                    apiMessage = result.message
                                }
                            }
                        } catch (e: Exception) {
                            apiMessage = e.message ?: "Gagal menyimpan pemeriksaan."
                        }
                    }
                }

                fun generateReport() {
                    val posyanduId = session?.posyanduId

                    if (posyanduId.isNullOrBlank()) {
                        apiMessage = "Posyandu ID tidak ditemukan. Silakan login ulang."
                        return
                    }

                    scope.launch {
                        try {
                            when (
                                val result = laporanRepository.createReport(
                                    posyanduId = posyanduId
                                )
                            ) {
                                is ApiResult.Success -> {
                                    val baseUrl = ApiConfig.DEFAULT_BASE_URL.trimEnd('/')
                                    val downloadUrl = "$baseUrl/laporan/download/${result.data.fileName}"

                                    try {
                                        startActivity(
                                            Intent(
                                                Intent.ACTION_VIEW,
                                                Uri.parse(downloadUrl)
                                            )
                                        )
                                    } catch (e: Exception) {
                                        apiMessage = "Laporan berhasil dibuat: ${result.data.fileName}"
                                    }
                                }

                                is ApiResult.Error -> {
                                    apiMessage = result.message
                                }
                            }
                        } catch (e: Exception) {
                            apiMessage = e.message ?: "Gagal mencetak laporan."
                        }
                    }
                }

                when (val current = screen) {
                    AppScreen.Login -> {
                        LoginScreen(
                            isLoading = loginLoading,
                            errorMessage = loginError,
                            onLogin = { email, password ->
                                scope.launch {
                                    try {
                                        loginLoading = true
                                        loginError = null

                                        when (
                                            val result = authRepository.login(
                                                email,
                                                password
                                            )
                                        ) {
                                            is ApiResult.Success -> {
                                                val user = result.data.user

                                                session = UserSession(
                                                    email = user.email,
                                                    name = user.nama,
                                                    role = parseApiRole(user.role),
                                                    token = result.data.token,
                                                    posyanduId = user.posyanduId
                                                )

                                                children.clear()
                                                schedules.clear()

                                                loadDashboardData()
                                                screen = AppScreen.Landing
                                            }

                                            is ApiResult.Error -> {
                                                loginError = result.message
                                            }
                                        }
                                    } catch (e: Exception) {
                                        loginError = e.message ?: "Login gagal."
                                    } finally {
                                        loginLoading = false
                                    }
                                }
                            }
                        )
                    }

                    AppScreen.Landing -> {
                        LandingScreen(
                            onOpenChildForm = {
                                screen = AppScreen.ChildForm
                            },
                            onOpenDashboard = {
                                loadDashboardData()
                                screen = AppScreen.Dashboard
                            },
                            onOpenExamination = {
                                loadDashboardData()
                                screen = AppScreen.PickChild
                            },
                            onLogout = {
                                authRepository.logout()
                                session = null
                                children.clear()
                                schedules.clear()
                                screen = AppScreen.Login
                            }
                        )
                    }

                    AppScreen.ChildForm -> {
                        ChildFormScreen(
                            onBack = {
                                screen = AppScreen.Landing
                            },
                            onSave = { childInput ->
                                addChild(childInput) {
                                    screen = AppScreen.Landing
                                }
                            }
                        )
                    }

                    AppScreen.PickChild -> {
                        ChildPickerScreen(
                            children = children,
                            onBack = {
                                screen = AppScreen.Landing
                            },
                            onOpenDetail = { childId ->
                                screen = AppScreen.DetailChild(childId)
                            },
                            onSaveMeasurement = ::addMeasurement
                        )
                    }

                    AppScreen.Dashboard -> {
                        DashboardScreen(
                            children = children,
                            schedules = schedules,
                            onBack = {
                                screen = AppScreen.Landing
                            },
                            onAddChild = {
                                screen = AppScreen.ChildForm
                            },
                            onOpenChild = { childId ->
                                screen = AppScreen.DetailChild(childId)
                            },
                            onAddSchedule = ::addSchedule,
                            onUpdateSchedule = ::updateSchedule,
                            onDeleteSchedule = ::deleteSchedule,
                            onSaveMeasurement = ::addMeasurement,
                            onDownloadData = ::generateReport
                        )
                    }

                    is AppScreen.DetailChild -> {
                        val selectedChild = children.firstOrNull {
                            it.id == current.childId
                        }

                        if (selectedChild == null) {
                            screen = AppScreen.Dashboard
                        } else {
                            ChildDetailScreen(
                                child = selectedChild,
                                onBack = {
                                    screen = AppScreen.Dashboard
                                },
                                onSaveMeasurement = ::addMeasurement
                            )
                        }
                    }
                }

                apiMessage?.let { message ->
                    AlertDialog(
                        onDismissRequest = {
                            apiMessage = null
                        },
                        title = {
                            Text("Informasi")
                        },
                        text = {
                            Text(message)
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    apiMessage = null
                                }
                            ) {
                                Text("OK")
                            }
                        }
                    )
                }
            }
        }
    }
}