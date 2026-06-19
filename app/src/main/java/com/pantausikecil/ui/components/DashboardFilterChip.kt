package com.pantausikecil.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DashboardFilterChip(
    text: String,
    color: Color,
    selected: Boolean,
    modifier: Modifier,
    isTablet: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(if (isTablet) figmaDp(68f) else figmaDp(42f))
            .border(
                width = if (selected) 3.dp else 2.dp,
                color = color,
                shape = RoundedCornerShape(999.dp)
            )
            .background(Color.White, RoundedCornerShape(999.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = color,
            fontSize = if (isTablet) figmaSp(30f) else figmaSp(17f),
            fontWeight = FontWeight.ExtraBold
        )
    }
}
