package com.pantausikecil.ui.screens

import com.pantausikecil.ui.components.ChildRowCard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pantausikecil.model.Child
import com.pantausikecil.model.Measurement
import com.pantausikecil.ui.components.BackHeader
import com.pantausikecil.ui.components.SearchField

@Composable
fun ChildPickerScreen(
    children: List<Child>,
    onBack: () -> Unit,
    onOpenDetail: (String) -> Unit,
    onSaveMeasurement: (String, Measurement) -> Unit
) {
    ChildPickerContent(
        children = children,
        title = "Pilih Anak",
        onBack = onBack,
        onOpenDetail = onOpenDetail,
        onSaveMeasurement = onSaveMeasurement
    )
}

@Composable
fun ChildPickerContent(
    children: List<Child>,
    title: String,
    onBack: () -> Unit,
    onOpenDetail: (String) -> Unit,
    onSaveMeasurement: (String, Measurement) -> Unit
) {
    var query by remember { mutableStateOf("") }
    var selectedChild by remember { mutableStateOf<Child?>(null) }
    var pendingResult by remember { mutableStateOf<Measurement?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    val filtered = children.filter { it.name.contains(query, ignoreCase = true) }

    Column(modifier = Modifier.fillMaxSize()) {
        BackHeader(onBack = onBack)
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 52.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 42.sp,
            textAlign = TextAlign.Center
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 44.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                SearchField(value = query, onValueChange = { query = it })
                Spacer(modifier = Modifier.height(18.dp))
            }
            items(filtered) { child ->
                ChildRowCard(
                    child = child,
                    onClick = { selectedChild = child }
                )
            }
        }
    }

    if (pendingResult == null) selectedChild?.let { child ->
        InputExaminationDialog(
            childId = child.id,
            onDismiss = { selectedChild = null },
            onResult = { measurement ->
                pendingResult = measurement
                selectedChild = child
            }
        )
    }

    pendingResult?.let { measurement ->
        val child = selectedChild
        if (child != null) {
            ResultExaminationDialog(
                measurement = measurement,
                onSave = {
                    onSaveMeasurement(child.id, measurement)
                    pendingResult = null
                    selectedChild = null
                    successMessage = "Data Pemeriksaan Tersimpan"
                },
                onSendWhatsapp = {
                    onSaveMeasurement(child.id, measurement)
                    pendingResult = null
                    selectedChild = null
                    successMessage = "Pemeriksaan Berhasil Terkirim"
                },
                onDismiss = {
                    pendingResult = null
                    selectedChild = null
                }
            )
        }
    }

    successMessage?.let { message ->
        SuccessDialog(message = message, onBackToMenu = { successMessage = null })
    }
}
