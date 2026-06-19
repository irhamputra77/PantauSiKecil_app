package com.pantausikecil.ui.screens

import com.pantausikecil.ui.components.PantauTextField
import com.pantausikecil.ui.components.PrimaryButton
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pantausikecil.model.PosyanduSchedule
import com.pantausikecil.ui.theme.BlueAction
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

private val DialogBackground = Color.White
private val DialogBorderColor = Color(0xFF8A8A8A)
private val DialogTextColor = Color(0xFF111111)
private val FieldBorderColor = Color(0xFF8E8E8E)
private val FieldIconColor = Color(0xFF9A9A9A)
private val FigmaGreen = Color(0xFF28D767)

private fun newScheduleId(): String {
    return "schedule-${System.currentTimeMillis()}"
}

private fun formatScheduleDate(millis: Long): String {
    return SimpleDateFormat(
        "dd MMM yyyy",
        Locale("id", "ID")
    ).format(Date(millis))
}

private fun parseScheduleDateMillis(value: String): Long? {
    if (value.isBlank()) return null

    val locale = Locale("id", "ID")
    val patterns = listOf(
        "dd MMM yyyy",
        "dd MMMM yyyy",
        "yyyy-MM-dd"
    )

    for (pattern in patterns) {
        try {
            val formatter = SimpleDateFormat(pattern, locale)
            formatter.isLenient = false
            return formatter.parse(value)?.time
        } catch (_: Exception) {
        }
    }

    return null
}

private fun parseTimeValue(value: String): Pair<Int, Int> {
    val parts = value.split(":")
    val hour = parts.getOrNull(0)?.toIntOrNull()?.coerceIn(0, 23) ?: 7
    val minute = parts.getOrNull(1)?.toIntOrNull()?.coerceIn(0, 59) ?: 0
    return hour to minute
}

@Composable
fun AddScheduleDialog(
    onDismiss: () -> Unit,
    onSave: (PosyanduSchedule) -> Unit
) {
    ScheduleInputDialog(
        title = "Tambah Jadwal",
        schedule = null,
        confirmText = "Tambah Jadwal",
        onDismiss = onDismiss,
        onSave = { title, activity, date, time ->
            onSave(
                PosyanduSchedule(
                    id = newScheduleId(),
                    title = title,
                    activity = activity,
                    date = date,
                    time = time
                )
            )
        }
    )
}

@Composable
fun EditScheduleDialog(
    schedule: PosyanduSchedule,
    onDismiss: () -> Unit,
    onSave: (PosyanduSchedule) -> Unit
) {
    ScheduleInputDialog(
        title = "Edit Jadwal",
        schedule = schedule,
        confirmText = "Selesai",
        onDismiss = onDismiss,
        onSave = { title, activity, date, time ->
            onSave(
                schedule.copy(
                    title = title,
                    activity = activity,
                    date = date,
                    time = time
                )
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScheduleInputDialog(
    title: String,
    schedule: PosyanduSchedule?,
    confirmText: String,
    onDismiss: () -> Unit,
    onSave: (title: String, activity: String, date: String, time: String) -> Unit
) {
    var inputTitle by remember(schedule?.id) {
        mutableStateOf(schedule?.title.orEmpty())
    }

    var activity by remember(schedule?.id) {
        mutableStateOf(schedule?.activity.orEmpty())
    }

    var date by remember(schedule?.id) {
        mutableStateOf(schedule?.date.orEmpty())
    }

    var time by remember(schedule?.id) {
        mutableStateOf(schedule?.time.orEmpty())
    }

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    var showTimePicker by remember {
        mutableStateOf(false)
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.84f)
                .widthIn(max = 360.dp),
            shape = RoundedCornerShape(6.dp),
            color = DialogBackground,
            border = BorderStroke(1.dp, DialogBorderColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        start = 28.dp,
                        end = 28.dp,
                        top = 24.dp,
                        bottom = 26.dp
                    )
            ) {
                ScheduleDialogTitle(
                    title = title,
                    onDismiss = onDismiss
                )

                Spacer(modifier = Modifier.height(22.dp))

                ScheduleTextInput(
                    label = "Judul",
                    value = inputTitle,
                    onValueChange = {
                        inputTitle = it
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                ScheduleTextInput(
                    label = "Kegiatan",
                    value = activity,
                    onValueChange = {
                        activity = it
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                SchedulePickerInput(
                    label = "Tanggal Jadwal",
                    value = date,
                    showCalendarIcon = true,
                    onClick = {
                        showDatePicker = true
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                SchedulePickerInput(
                    label = "Pukul",
                    value = time,
                    showCalendarIcon = false,
                    onClick = {
                        showTimePicker = true
                    }
                )

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = {
                        onSave(
                            inputTitle.trim(),
                            activity.trim(),
                            date.trim(),
                            time.trim()
                        )
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .height(50.dp)
                        .shadow(
                            elevation = 14.dp,
                            shape = RoundedCornerShape(10.dp),
                            clip = false
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = FigmaGreen,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp,
                        focusedElevation = 0.dp,
                        hoveredElevation = 0.dp
                    )
                ) {
                    Text(
                        text = confirmText,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = parseScheduleDateMillis(date)
                ?: Calendar.getInstance().timeInMillis
        )

        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { selectedMillis ->
                            date = formatScheduleDate(selectedMillis)
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("Pilih")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                    }
                ) {
                    Text("Batal")
                }
            }
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }

    if (showTimePicker) {
        val initialTime = parseTimeValue(time)

        val timePickerState = rememberTimePickerState(
            initialHour = initialTime.first,
            initialMinute = initialTime.second,
            is24Hour = true
        )

        AlertDialog(
            onDismissRequest = {
                showTimePicker = false
            },
            text = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    TimePicker(
                        state = timePickerState
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        time = "%02d:%02d".format(
                            timePickerState.hour,
                            timePickerState.minute
                        )
                        showTimePicker = false
                    }
                ) {
                    Text("Pilih")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showTimePicker = false
                    }
                ) {
                    Text("Batal")
                }
            }
        )
    }
}

@Composable
private fun ScheduleDialogTitle(
    title: String,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(38.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 40.dp),
            color = DialogTextColor,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 26.sp,
            textAlign = TextAlign.Center,
            maxLines = 1
        )

        IconButton(
            onClick = onDismiss,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(34.dp)
        ) {
            Text(
                text = "x",
                color = Color(0xFF777777),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }
    }
}

@Composable
private fun ScheduleTextInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            color = DialogTextColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(
                color = DialogTextColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .border(
                    width = 1.dp,
                    color = FieldBorderColor,
                    shape = RoundedCornerShape(10.dp)
                ),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    innerTextField()
                }
            }
        )
    }
}

@Composable
private fun SchedulePickerInput(
    label: String,
    value: String,
    showCalendarIcon: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            color = DialogTextColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .border(
                    width = 1.dp,
                    color = FieldBorderColor,
                    shape = RoundedCornerShape(10.dp)
                )
                .clickable {
                    onClick()
                }
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showCalendarIcon) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Pilih tanggal",
                        tint = FieldIconColor,
                        modifier = Modifier.size(26.dp)
                    )

                    Spacer(modifier = Modifier.width(18.dp))
                }

                Text(
                    text = value,
                    color = DialogTextColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun ConfirmationDialog(
    message: String,
    positiveText: String,
    positiveColor: Color,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    cancelColor: Color = BlueAction
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.86f)
                .widthIn(max = 420.dp),
            shape = RoundedCornerShape(6.dp),
            color = DialogBackground,
            border = BorderStroke(1.dp, DialogBorderColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 24.dp,
                        end = 24.dp,
                        top = 42.dp,
                        bottom = 32.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = message,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = DialogTextColor,
                    fontWeight = FontWeight.Normal,
                    fontSize = 22.sp,
                    lineHeight = 30.sp
                )

                Spacer(modifier = Modifier.height(36.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Button(
                        onClick = onCancel,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .shadow(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(8.dp),
                                clip = false
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = cancelColor,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 0.dp,
                            focusedElevation = 0.dp,
                            hoveredElevation = 0.dp
                        )
                    ) {
                        Text(
                            text = "Batal",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }

                    Button(
                        onClick = onConfirm,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .shadow(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(8.dp),
                                clip = false
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = positiveColor,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 0.dp,
                            focusedElevation = 0.dp,
                            hoveredElevation = 0.dp
                        )
                    ) {
                        Text(
                            text = positiveText,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DownloadDataDialog(
    onDismiss: () -> Unit,
    onPrint: () -> Unit
) {
    var selectedMonth by remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            DownloadDialogTitle(
                title = "Pilih Periode Waktu",
                onDismiss = onDismiss
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                PantauTextField(
                    value = selectedMonth,
                    label = "Pilih Bulan",
                    placeholder = "Contoh: Januari 2026",
                    onValueChange = {
                        selectedMonth = it
                    }
                )

                Text(
                    text = "Preview Ringkas",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )

                PreviewDataRow(
                    label = "Total Anak",
                    value = "240"
                )

                PreviewDataRow(
                    label = "Total Pemeriksaan",
                    value = "1.230"
                )

                PreviewDataRow(
                    label = "Beresiko Stunting",
                    value = "40",
                    percent = "(12.5%)"
                )

                PreviewDataRow(
                    label = "Stunting",
                    value = "90",
                    percent = "(16.7%)"
                )
            }
        },
        confirmButton = {
            PrimaryButton(
                text = "Cetak Data",
                modifier = Modifier.fillMaxWidth(),
                onClick = onPrint
            )
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Batal")
            }
        }
    )
}

@Composable
private fun DownloadDialogTitle(
    title: String,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            modifier = Modifier.align(Alignment.Center),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )

        IconButton(
            onClick = onDismiss,
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Text(
                text = "x",
                color = Color(0xFF777777),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }
    }
}

@Composable
private fun PreviewDataRow(
    label: String,
    value: String,
    percent: String = ""
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp
        )

        Text(
            text = value,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )

        if (percent.isNotBlank()) {
            Text(
                text = " $percent",
                fontSize = 16.sp,
                color = Color(0xFFEF626C)
            )
        }
    }
}