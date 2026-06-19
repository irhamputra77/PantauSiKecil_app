package com.pantausikecil.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.pantausikecil.model.Child

@Composable
fun ChildDataRow(
    child: Child,
    isTablet: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = child.name,
                color = Color.Black,
                fontSize = if (isTablet) figmaSp(42f) else figmaSp(24f),
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = child.parentName,
                color = DashboardGreyText,
                fontSize = if (isTablet) figmaSp(28f) else figmaSp(16f)
            )

            Text(
                text = child.ageLabel,
                color = DashboardGreyText,
                fontSize = if (isTablet) figmaSp(28f) else figmaSp(16f)
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(if (isTablet) figmaDp(18f) else figmaDp(8f))
            ) {
                DashboardStatusPill(
                    text = nutritionStatusLabel(child.status),
                    background = nutritionStatusColor(child.status),
                    isTablet = isTablet
                )

                DashboardStatusPill(
                    text = stuntingStatusLabel(child.status),
                    background = stuntingStatusColor(child.status),
                    isTablet = isTablet
                )
            }

            Spacer(modifier = Modifier.height(if (isTablet) figmaDp(16f) else figmaDp(8f)))

            Text(
                text = child.gender.label,
                color = DashboardGreyText,
                fontSize = if (isTablet) figmaSp(28f) else figmaSp(15f)
            )

            Text(
                text = "Pemeriksaan : ${child.measurements.size}",
                color = DashboardGreyText,
                fontSize = if (isTablet) figmaSp(28f) else figmaSp(15f)
            )
        }
    }
}
