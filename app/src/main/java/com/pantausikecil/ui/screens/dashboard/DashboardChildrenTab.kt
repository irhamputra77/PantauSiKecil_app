package com.pantausikecil.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.pantausikecil.model.Child
import com.pantausikecil.model.GrowthStatus
import com.pantausikecil.ui.components.ChildrenListPanel
import com.pantausikecil.ui.components.DashboardBlueButton
import com.pantausikecil.ui.components.DashboardFilterChip
import com.pantausikecil.ui.components.DashboardGradientButton
import com.pantausikecil.ui.components.DashboardOrange
import com.pantausikecil.ui.components.DashboardRedStatus
import com.pantausikecil.ui.components.DashboardSearchInput
import com.pantausikecil.ui.components.figmaDp
import com.pantausikecil.ui.components.figmaSp

@Composable
fun DashboardChildrenTab(
    children: List<Child>,
    onBack: () -> Unit,
    onAddChild: () -> Unit,
    onOpenChild: (String) -> Unit,
    onDownload: () -> Unit,
    bottomPadding: Dp
) {
    var query by remember {
        mutableStateOf("")
    }

    var selectedFilter by remember {
        mutableStateOf<String?>(null)
    }

    val filtered = remember(children, query, selectedFilter) {
        children.filter { child ->
            val matchesQuery =
                child.name.contains(query, ignoreCase = true) ||
                    child.parentName.contains(query, ignoreCase = true)

            val matchesFilter = when (selectedFilter) {
                "Normal" -> child.status == GrowthStatus.Normal || child.status == GrowthStatus.GiziCukup
                "Beresiko" -> child.status == GrowthStatus.Risk || child.status == GrowthStatus.GiziKurang
                "Stunting" -> child.status == GrowthStatus.Stunting
                else -> true
            }

            matchesQuery && matchesFilter
        }
    }

    DashboardScaffold(
        title = "Data Anak",
        onBack = onBack,
        bottomPadding = bottomPadding
    ) { isTablet ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                if (isTablet) figmaDp(64f) else figmaDp(28f)
            )
        ) {
            DashboardGradientButton(
                text = "Tambah Data",
                height = if (isTablet) figmaDp(76f) else figmaDp(48f),
                radius = if (isTablet) figmaDp(12f) else figmaDp(8f),
                fontSize = if (isTablet) figmaSp(32f) else figmaSp(18f),
                modifier = Modifier.weight(1f),
                onClick = onAddChild
            )

            DashboardBlueButton(
                text = "Cetak Data",
                height = if (isTablet) figmaDp(76f) else figmaDp(48f),
                radius = if (isTablet) figmaDp(12f) else figmaDp(8f),
                fontSize = if (isTablet) figmaSp(32f) else figmaSp(18f),
                modifier = Modifier.weight(1f),
                onClick = onDownload
            )
        }

        Spacer(
            modifier = Modifier.height(
                if (isTablet) figmaDp(38f) else figmaDp(22f)
            )
        )

        DashboardSearchInput(
            value = query,
            onValueChange = {
                query = it
            },
            isTablet = isTablet
        )

        Spacer(
            modifier = Modifier.height(
                if (isTablet) figmaDp(54f) else figmaDp(28f)
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                if (isTablet) figmaDp(40f) else figmaDp(16f)
            )
        ) {
            DashboardFilterChip(
                text = "Normal",
                color = Color(0xFF43B96A),
                selected = selectedFilter == "Normal",
                modifier = Modifier.weight(1f),
                isTablet = isTablet,
                onClick = {
                    selectedFilter = if (selectedFilter == "Normal") null else "Normal"
                }
            )

            DashboardFilterChip(
                text = "Beresiko",
                color = DashboardOrange,
                selected = selectedFilter == "Beresiko",
                modifier = Modifier.weight(1f),
                isTablet = isTablet,
                onClick = {
                    selectedFilter = if (selectedFilter == "Beresiko") null else "Beresiko"
                }
            )

            DashboardFilterChip(
                text = "Stunting",
                color = DashboardRedStatus,
                selected = selectedFilter == "Stunting",
                modifier = Modifier.weight(1f),
                isTablet = isTablet,
                onClick = {
                    selectedFilter = if (selectedFilter == "Stunting") null else "Stunting"
                }
            )
        }

        Spacer(
            modifier = Modifier.height(
                if (isTablet) figmaDp(54f) else figmaDp(28f)
            )
        )

        if (filtered.isEmpty()) {
            EmptyChildrenPanel(
                isTablet = isTablet
            )
        } else {
            ChildrenListPanel(
                children = filtered,
                isTablet = isTablet,
                onOpenChild = onOpenChild
            )
        }
    }
}

@Composable
private fun EmptyChildrenPanel(
    isTablet: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (isTablet) figmaDp(770f) else figmaDp(430f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Tidak ada data anak",
            color = Color(0xFF777777),
            fontSize = if (isTablet) figmaSp(30f) else figmaSp(18f),
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }
}
