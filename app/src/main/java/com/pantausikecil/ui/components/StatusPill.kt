package com.pantausikecil.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pantausikecil.model.GrowthStatus

@Composable
fun DashboardStatusPill(
    text: String,
    background: Color,
    isTablet: Boolean
) {
    Box(
        modifier = Modifier
            .height(if (isTablet) figmaDp(48f) else figmaDp(28f))
            .clip(RoundedCornerShape(999.dp))
            .background(background)
            .padding(horizontal = if (isTablet) figmaDp(20f) else figmaDp(10f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = if (isTablet) figmaSp(20f) else figmaSp(11f),
            fontWeight = FontWeight.ExtraBold
        )
    }
}

fun nutritionStatusLabel(status: GrowthStatus): String {
    return when (status) {
        GrowthStatus.GiziKurang,
        GrowthStatus.Risk,
        GrowthStatus.Stunting -> "Gizi Kurang"
        else -> "Gizi Cukup"
    }
}

fun nutritionStatusColor(status: GrowthStatus): Color {
    return when (status) {
        GrowthStatus.GiziKurang,
        GrowthStatus.Risk,
        GrowthStatus.Stunting -> DashboardOrangeDark
        else -> DashboardPrimaryGreen
    }
}

fun stuntingStatusLabel(status: GrowthStatus): String {
    return when (status) {
        GrowthStatus.Stunting -> "Stunting"
        GrowthStatus.Risk,
        GrowthStatus.GiziKurang -> "Beresiko"
        else -> "Normal"
    }
}

fun stuntingStatusColor(status: GrowthStatus): Color {
    return when (status) {
        GrowthStatus.Stunting -> DashboardRedStatus
        GrowthStatus.Risk,
        GrowthStatus.GiziKurang -> DashboardOrange
        else -> Color(0xFF4CBF73)
    }
}
