package com.pantausikecil.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

@Composable
fun DashboardGradientButton(
    text: String,
    height: Dp,
    radius: Dp,
    fontSize: TextUnit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    DashboardButtonBase(
        text = text,
        height = height,
        radius = radius,
        fontSize = fontSize,
        modifier = modifier,
        brush = Brush.horizontalGradient(
            listOf(DashboardPrimaryGreen, DashboardPrimaryGreenDark)
        ),
        onClick = onClick
    )
}

@Composable
fun DashboardBlueButton(
    text: String,
    height: Dp,
    radius: Dp,
    fontSize: TextUnit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    DashboardButtonBase(
        text = text,
        height = height,
        radius = radius,
        fontSize = fontSize,
        modifier = modifier,
        brush = Brush.horizontalGradient(listOf(DashboardBlue, DashboardBlue)),
        onClick = onClick
    )
}

@Composable
fun DashboardRedButton(
    text: String,
    height: Dp,
    radius: Dp,
    fontSize: TextUnit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    DashboardButtonBase(
        text = text,
        height = height,
        radius = radius,
        fontSize = fontSize,
        modifier = modifier,
        brush = Brush.horizontalGradient(listOf(DashboardRed, DashboardRed)),
        onClick = onClick
    )
}

@Composable
fun DashboardDarkButton(
    text: String,
    height: Dp,
    radius: Dp,
    fontSize: TextUnit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    DashboardButtonBase(
        text = text,
        height = height,
        radius = radius,
        fontSize = fontSize,
        modifier = modifier,
        brush = Brush.horizontalGradient(listOf(DashboardDarkGreen, DashboardDarkGreen)),
        onClick = onClick
    )
}

@Composable
private fun DashboardButtonBase(
    text: String,
    height: Dp,
    radius: Dp,
    fontSize: TextUnit,
    modifier: Modifier,
    brush: Brush,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(height)
            .shadow(
                elevation = figmaDp(16f),
                shape = RoundedCornerShape(radius),
                ambientColor = Color.Black.copy(alpha = 0.18f),
                spotColor = Color.Black.copy(alpha = 0.18f)
            )
            .clip(RoundedCornerShape(radius))
            .background(brush)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = fontSize,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )
    }
}
