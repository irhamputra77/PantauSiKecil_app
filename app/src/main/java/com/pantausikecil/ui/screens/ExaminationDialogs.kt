package com.pantausikecil.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pantausikecil.model.Measurement
import com.pantausikecil.model.classifyGrowth
import com.pantausikecil.ui.components.IconCheck
import com.pantausikecil.ui.components.IconClose
import com.pantausikecil.ui.components.PantauTextField
import com.pantausikecil.ui.components.PrimaryButton
import com.pantausikecil.ui.components.StatusChip
import com.pantausikecil.ui.theme.BlueAction
import com.pantausikecil.ui.theme.PrimaryGreen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private fun newMeasurementId(): String {
    return "measurement-${System.currentTimeMillis()}"
}

private fun todayLabel(): String {
    return SimpleDateFormat(
        "dd MMMM yyyy",
        Locale("id", "ID")
    ).format(Date())
}

@Composable
private fun ExaminationDialogTitle(
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
            fontSize = 28.sp,
            textAlign = TextAlign.Center
        )

        IconButton(
            onClick = onDismiss,
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = IconClose,
                contentDescription = "Tutup"
            )
        }
    }
}

@Composable
fun InputExaminationDialog(
    childId: String,
    onDismiss: () -> Unit,
    onResult: (Measurement) -> Unit
) {
    var height by remember {
        mutableStateOf("")
    }

    var weight by remember {
        mutableStateOf("")
    }

    var head by remember {
        mutableStateOf("")
    }

    var arm by remember {
        mutableStateOf("")
    }

    var method by remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            ExaminationDialogTitle(
                title = "Masukkan Pemeriksaan",
                onDismiss = onDismiss
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                PantauTextField(
                    value = height,
                    label = "Tinggi Badan",
                    placeholder = "Contoh: 140",
                    leadingIcon = Icons.Default.Straighten,
                    trailingText = "cm",
                    onValueChange = {
                        height = it
                    }
                )

                PantauTextField(
                    value = weight,
                    label = "Berat Badan",
                    leadingIcon = Icons.Default.MonitorWeight,
                    trailingText = "kg",
                    onValueChange = {
                        weight = it
                    }
                )

                PantauTextField(
                    value = head,
                    label = "Lingkar Kepala",
                    leadingIcon = Icons.Default.Straighten,
                    trailingText = "cm",
                    onValueChange = {
                        head = it
                    }
                )

                PantauTextField(
                    value = arm,
                    label = "Lingkar Lengan Atas",
                    leadingIcon = Icons.Default.Straighten,
                    trailingText = "cm",
                    onValueChange = {
                        arm = it
                    }
                )

                PantauTextField(
                    value = method,
                    label = "Cara Ukur",
                    onValueChange = {
                        method = it
                    }
                )
            }
        },
        confirmButton = {
            PrimaryButton(
                text = "Periksa",
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val heightValue = height.replace(',', '.').toDoubleOrNull() ?: 0.0
                    val weightValue = weight.replace(',', '.').toDoubleOrNull() ?: 0.0
                    val headValue = head.replace(',', '.').toDoubleOrNull() ?: 0.0
                    val armValue = arm.replace(',', '.').toDoubleOrNull() ?: 0.0
                    val status = classifyGrowth(heightValue, weightValue)

                    onResult(
                        Measurement(
                            id = newMeasurementId(),
                            date = todayLabel(),
                            heightCm = heightValue,
                            weightKg = weightValue,
                            headCircumferenceCm = headValue,
                            armCircumferenceCm = armValue,
                            measureMethod = method.ifBlank {
                                "Berdiri"
                            },
                            status = status
                        )
                    )
                }
            )
        }
    )
}

@Composable
fun ResultExaminationDialog(
    measurement: Measurement,
    onSave: () -> Unit,
    onSendWhatsapp: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            ExaminationDialogTitle(
                title = "Hasil Pemeriksaan",
                onDismiss = onDismiss
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                ResultRow(
                    label = "Tinggi Badan",
                    value = "${measurement.heightCm} cm"
                )

                ResultRow(
                    label = "Berat Badan",
                    value = "${measurement.weightKg} kg"
                )

                ResultRow(
                    label = "Lingkar Kepala",
                    value = "${measurement.headCircumferenceCm} cm"
                )

                ResultRow(
                    label = "Lingkar Lengan Atas",
                    value = "${measurement.armCircumferenceCm} cm"
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Status",
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Bold
                    )

                    StatusChip(measurement.status)
                }
            }
        },
        confirmButton = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Button(
                    onClick = onSave,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BlueAction
                    ),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "Selesai",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                Button(
                    onClick = onSendWhatsapp,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryGreen
                    ),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "Kirim Whatsapp",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    )
}

@Composable
private fun ResultRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            fontSize = 17.sp
        )

        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp
        )
    }
}

@Composable
fun EditExaminationDialog(
    measurement: Measurement,
    onDismiss: () -> Unit,
    onSave: (Measurement) -> Unit
) {
    var height by remember {
        mutableStateOf(measurement.heightCm.toString())
    }

    var weight by remember {
        mutableStateOf(measurement.weightKg.toString())
    }

    var head by remember {
        mutableStateOf(measurement.headCircumferenceCm.toString())
    }

    var arm by remember {
        mutableStateOf(measurement.armCircumferenceCm.toString())
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            ExaminationDialogTitle(
                title = "Edit Pemeriksaan",
                onDismiss = onDismiss
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Text(
                    text = "Tanggal : ${measurement.date}",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                PantauTextField(
                    value = height,
                    label = "Tinggi Badan",
                    trailingText = "cm",
                    onValueChange = {
                        height = it
                    }
                )

                PantauTextField(
                    value = weight,
                    label = "Berat Badan",
                    trailingText = "kg",
                    onValueChange = {
                        weight = it
                    }
                )

                PantauTextField(
                    value = head,
                    label = "Lingkar Kepala",
                    trailingText = "cm",
                    onValueChange = {
                        head = it
                    }
                )

                PantauTextField(
                    value = arm,
                    label = "Lingkar Lengan Atas",
                    trailingText = "cm",
                    onValueChange = {
                        arm = it
                    }
                )
            }
        },
        confirmButton = {
            PrimaryButton(
                text = "Selesai",
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val heightValue = height.replace(',', '.').toDoubleOrNull()
                        ?: measurement.heightCm

                    val weightValue = weight.replace(',', '.').toDoubleOrNull()
                        ?: measurement.weightKg

                    val headValue = head.replace(',', '.').toDoubleOrNull()
                        ?: measurement.headCircumferenceCm

                    val armValue = arm.replace(',', '.').toDoubleOrNull()
                        ?: measurement.armCircumferenceCm

                    onSave(
                        measurement.copy(
                            heightCm = heightValue,
                            weightKg = weightValue,
                            headCircumferenceCm = headValue,
                            armCircumferenceCm = armValue,
                            status = classifyGrowth(heightValue, weightValue)
                        )
                    )
                }
            )
        }
    )
}

@Composable
fun SuccessDialog(
    message: String,
    onBackToMenu: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onBackToMenu,
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        PrimaryGreen,
                        RoundedCornerShape(6.dp)
                    )
                    .padding(42.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(148.dp)
                        .background(
                            Color.White.copy(alpha = 0.25f),
                            RoundedCornerShape(42.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = IconCheck,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(104.dp)
                    )
                }

                Spacer(
                    modifier = Modifier.height(34.dp)
                )

                Text(
                    text = message,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(
                    modifier = Modifier.height(32.dp)
                )

                Button(
                    onClick = onBackToMenu,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "Kembali ke Menu",
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        confirmButton = {},
        containerColor = Color.Transparent
    )
}