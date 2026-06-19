package com.pantausikecil.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.pantausikecil.model.PosyanduSchedule
import com.pantausikecil.ui.components.DashboardGradientButton
import com.pantausikecil.ui.components.DashboardPrimaryGreen
import com.pantausikecil.ui.components.DashboardRed
import com.pantausikecil.ui.components.ScheduleCard
import com.pantausikecil.ui.components.figmaDp
import com.pantausikecil.ui.components.figmaSp
import com.pantausikecil.ui.screens.AddScheduleDialog
import com.pantausikecil.ui.screens.ConfirmationDialog
import com.pantausikecil.ui.screens.EditScheduleDialog
import com.pantausikecil.ui.screens.SuccessDialog

@Composable
fun DashboardScheduleTab(
    schedules: List<PosyanduSchedule>,
    onBack: () -> Unit,
    onAddSchedule: (PosyanduSchedule) -> Unit,
    onUpdateSchedule: (PosyanduSchedule) -> Unit,
    onDeleteSchedule: (String) -> Unit,
    bottomPadding: Dp
) {
    var showAdd by remember {
        mutableStateOf(false)
    }

    var editing by remember {
        mutableStateOf<PosyanduSchedule?>(null)
    }

    var deleting by remember {
        mutableStateOf<PosyanduSchedule?>(null)
    }

    var sending by remember {
        mutableStateOf<PosyanduSchedule?>(null)
    }

    var successMessage by remember {
        mutableStateOf<String?>(null)
    }

    DashboardScaffold(
        title = "Jadwal Posyandu",
        onBack = onBack,
        bottomPadding = bottomPadding
    ) { isTablet ->
        DashboardGradientButton(
            text = "Tambah Jadwal",
            height = if (isTablet) {
                figmaDp(80f)
            } else {
                figmaDp(48f)
            },
            radius = if (isTablet) {
                figmaDp(12f)
            } else {
                figmaDp(8f)
            },
            fontSize = if (isTablet) {
                figmaSp(34f)
            } else {
                figmaSp(20f)
            },
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                showAdd = true
            }
        )

        Spacer(
            modifier = Modifier.height(
                if (isTablet) {
                    figmaDp(120f)
                } else {
                    figmaDp(55f)
                }
            )
        )

        if (schedules.isEmpty()) {
            EmptyScheduleData(
                isTablet = isTablet
            )
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    if (isTablet) {
                        figmaDp(48f)
                    } else {
                        figmaDp(26f)
                    }
                )
            ) {
                schedules.chunked(2).forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(
                            if (isTablet) {
                                figmaDp(50f)
                            } else {
                                figmaDp(20f)
                            }
                        )
                    ) {
                        rowItems.forEach { schedule ->
                            ScheduleCard(
                                schedule = schedule,
                                modifier = Modifier.weight(1f),
                                isTablet = isTablet,
                                onSend = {
                                    sending = schedule
                                },
                                onEdit = {
                                    editing = schedule
                                },
                                onDelete = {
                                    deleting = schedule
                                }
                            )
                        }

                        if (rowItems.size == 1) {
                            Spacer(
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }

    if (showAdd) {
        AddScheduleDialog(
            onDismiss = {
                showAdd = false
            },
            onSave = { schedule ->
                onAddSchedule(schedule)
                showAdd = false
                successMessage = "Data Berhasil Tersimpan"
            }
        )
    }

    editing?.let { schedule ->
        EditScheduleDialog(
            schedule = schedule,
            onDismiss = {
                editing = null
            },
            onSave = { updatedSchedule ->
                onUpdateSchedule(updatedSchedule)
                editing = null
                successMessage = "Data Berhasil di Edit"
            }
        )
    }

    deleting?.let { schedule ->
        ConfirmationDialog(
            message = "Apakah anda yakin\ningin menghapus?",
            positiveText = "Hapus",
            positiveColor = DashboardRed,
            onCancel = {
                deleting = null
            },
            onConfirm = {
                onDeleteSchedule(schedule.id)
                deleting = null
            }
        )
    }

    sending?.let {
        ConfirmationDialog(
            message = "Pastikan Jadwal Sudah Benar",
            positiveText = "Kirim",
            positiveColor = DashboardPrimaryGreen,
            onCancel = {
                sending = null
            },
            onConfirm = {
                sending = null
                successMessage = "Data Berhasil Terkirim"
            }
        )
    }

    successMessage?.let { message ->
        SuccessDialog(
            message = message,
            onBackToMenu = {
                successMessage = null
            }
        )
    }
}

@Composable
private fun EmptyScheduleData(
    isTablet: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(
                if (isTablet) {
                    figmaDp(420f)
                } else {
                    figmaDp(240f)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Tidak ada data",
            color = Color(0xFF6D6D6D),
            fontSize = if (isTablet) {
                figmaSp(34f)
            } else {
                figmaSp(20f)
            },
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }
}