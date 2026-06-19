package com.pantausikecil.ui.screens.dashboard

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import com.pantausikecil.model.Child
import com.pantausikecil.model.Measurement
import com.pantausikecil.ui.components.ChildrenListPanel
import com.pantausikecil.ui.components.figmaDp
import com.pantausikecil.ui.screens.InputExaminationDialog
import com.pantausikecil.ui.screens.ResultExaminationDialog
import com.pantausikecil.ui.screens.SuccessDialog

@Composable
fun DashboardExaminationTab(
    children: List<Child>,
    onBack: () -> Unit,
    onOpenChild: (String) -> Unit,
    onSaveMeasurement: (String, Measurement) -> Unit,
    bottomPadding: Dp
) {
    var selectedChild by remember { mutableStateOf<Child?>(null) }
    var pendingResult by remember { mutableStateOf<Measurement?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    DashboardScaffold(
        title = "Pilih Anak",
        onBack = onBack,
        bottomPadding = bottomPadding
    ) { isTablet ->
        Spacer(modifier = androidx.compose.ui.Modifier.height(if (isTablet) figmaDp(30f) else figmaDp(18f)))

        ChildrenListPanel(
            children = children,
            isTablet = isTablet,
            onOpenChild = { childId ->
                selectedChild = children.firstOrNull { it.id == childId }
                if (selectedChild == null) onOpenChild(childId)
            }
        )
    }

    if (pendingResult == null) {
        selectedChild?.let { child ->
            InputExaminationDialog(
                childId = child.id,
                onDismiss = { selectedChild = null },
                onResult = { measurement ->
                    pendingResult = measurement
                }
            )
        }
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
        SuccessDialog(
            message = message,
            onBackToMenu = { successMessage = null }
        )
    }
}
