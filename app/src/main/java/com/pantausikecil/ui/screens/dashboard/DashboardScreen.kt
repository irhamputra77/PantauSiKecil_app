package com.pantausikecil.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.pantausikecil.model.Child
import com.pantausikecil.model.Measurement
import com.pantausikecil.model.PosyanduSchedule
import com.pantausikecil.ui.components.DashboardBottomBar
import com.pantausikecil.ui.components.DashboardHeaderGreen
import com.pantausikecil.ui.components.DashboardHeaderGreenDark
import com.pantausikecil.ui.components.figmaDp
import com.pantausikecil.ui.screens.DownloadDataDialog

@Composable
fun DashboardScreen(
    children: List<Child>,
    schedules: List<PosyanduSchedule>,
    onBack: () -> Unit,
    onAddChild: () -> Unit,
    onOpenChild: (String) -> Unit,
    onAddSchedule: (PosyanduSchedule) -> Unit,
    onUpdateSchedule: (PosyanduSchedule) -> Unit,
    onDeleteSchedule: (String) -> Unit,
    onSaveMeasurement: (String, Measurement) -> Unit,
    onDownloadData: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(DashboardTab.Home) }
    var showDownloadDialog by remember { mutableStateOf(false) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(DashboardHeaderGreen, DashboardHeaderGreenDark)
                )
            )
            .imePadding()
    ) {
        val isTablet = maxWidth >= figmaDp(1050f)
        val bottomBarHeight = if (isTablet) figmaDp(170f) else figmaDp(105f)
        val bottomBarBottom = if (isTablet) figmaDp(45f) else figmaDp(24f)
        val horizontalMargin = if (isTablet) figmaDp(78f) else figmaDp(32f)
        val bottomPadding = bottomBarHeight + bottomBarBottom + figmaDp(38f)

        when (selectedTab) {
            DashboardTab.Home -> DashboardHomeTab(
                children = children,
                schedules = schedules,
                onBack = onBack,
                bottomPadding = bottomPadding
            )

            DashboardTab.Schedule -> DashboardScheduleTab(
                schedules = schedules,
                onBack = onBack,
                onAddSchedule = onAddSchedule,
                onUpdateSchedule = onUpdateSchedule,
                onDeleteSchedule = onDeleteSchedule,
                bottomPadding = bottomPadding
            )

            DashboardTab.Data -> DashboardChildrenTab(
                children = children,
                onBack = onBack,
                onAddChild = onAddChild,
                onOpenChild = onOpenChild,
                onDownload = { showDownloadDialog = true },
                bottomPadding = bottomPadding
            )

            DashboardTab.Check -> DashboardExaminationTab(
                children = children,
                onBack = onBack,
                onOpenChild = onOpenChild,
                onSaveMeasurement = onSaveMeasurement,
                bottomPadding = bottomPadding
            )
        }

        DashboardBottomBar(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = horizontalMargin, end = horizontalMargin, bottom = bottomBarBottom)
                .height(bottomBarHeight)
        )
    }

    if (showDownloadDialog) {
        DownloadDataDialog(
            onDismiss = {
                showDownloadDialog = false
            },
            onPrint = {
                showDownloadDialog = false
                onDownloadData()
            }
        )
    }
}
