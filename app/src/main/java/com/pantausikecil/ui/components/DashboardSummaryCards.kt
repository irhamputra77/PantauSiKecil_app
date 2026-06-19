package com.pantausikecil.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun LargeSummaryCard(
    title: String,
    value: String,
    gradient: Brush,
    modifier: Modifier,
    isTablet: Boolean
) {
    Box(
        modifier = modifier
            .height(if (isTablet) figmaDp(270f) else figmaDp(150f))
            .clip(RoundedCornerShape(if (isTablet) figmaDp(14f) else figmaDp(10f)))
            .background(gradient)
            .padding(if (isTablet) figmaDp(30f) else figmaDp(18f))
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = if (isTablet) figmaSp(48f) else figmaSp(25f),
            fontWeight = FontWeight.Normal,
            modifier = Modifier.align(Alignment.TopStart)
        )

        Text(
            text = value,
            color = Color.White,
            fontSize = if (isTablet) figmaSp(112f) else figmaSp(55f),
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}

@Composable
fun SmallSummaryCard(
    label: String,
    value: String,
    gradient: Brush,
    modifier: Modifier,
    isTablet: Boolean
) {
    Box(
        modifier = modifier
            .height(if (isTablet) figmaDp(155f) else figmaDp(86f))
            .clip(RoundedCornerShape(if (isTablet) figmaDp(12f) else figmaDp(8f)))
            .background(gradient)
            .padding(horizontal = if (isTablet) figmaDp(18f) else figmaDp(10f)),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = value,
                color = Color.White,
                fontSize = if (isTablet) figmaSp(32f) else figmaSp(48f),
            )

            Spacer(modifier = Modifier.width(if (isTablet) figmaDp(18f) else figmaDp(8f)))

            Text(
                text = label,
                color = Color.White,
                fontSize = if (isTablet) figmaSp(32f) else figmaSp(17f),
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(top = if (isTablet) figmaDp(25f) else figmaDp(12f))
            )
        }
    }
}

@Composable
fun DashboardTextPill(
    text: String,
    background: Color,
    textColor: Color,
    isTablet: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(if (isTablet) figmaDp(55f) else figmaDp(34f))
            .clip(RoundedCornerShape(999.dp))
            .background(background),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = if (isTablet) figmaSp(25f) else figmaSp(14f),
            fontWeight = FontWeight.Normal
        )
    }
}
