package com.pantausikecil.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private const val FIGMA_TO_COMPOSE_SCALE = 1.5f

private fun figmaDp(px: Float): Dp {
    return (px / FIGMA_TO_COMPOSE_SCALE).dp
}

private fun figmaSp(px: Float): TextUnit {
    return (px / FIGMA_TO_COMPOSE_SCALE).sp
}

@Composable
fun LandingScreen(
    onOpenChildForm: () -> Unit,
    onOpenDashboard: () -> Unit,
    onOpenExamination: () -> Unit,
    onLogout: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF48B96A),
                        Color(0xFF67CC86),
                        Color(0xFFDDF6E4)
                    )
                )
            )
    ) {
        val isTablet = maxWidth >= 700.dp

        /*
         * Masukkan ukuran dari Figma dalam px.
         * Helper figmaDp() dan figmaSp() akan mengubah px ke dp/sp.
         *
         * Target asumsi:
         * Figma/device px: 1200 x 2000
         * Compose logical size kira-kira: 800 x 1333 dp
         * Jadi 1 dp/sp ≈ 1.5 px
         */

        val titleTopSpace = if (isTablet) figmaDp(455f) else figmaDp(230f)
        val titleSize = if (isTablet) figmaSp(70f) else figmaSp(45f)
        val titleToMenuGap = if (isTablet) figmaDp(120f) else figmaDp(85f)

        val tileSize = if (isTablet) figmaDp(400f) else figmaDp(190f)
        val cardGap = if (isTablet) figmaDp(80f) else figmaDp(45f)
        val rowGap = if (isTablet) figmaDp(80f) else figmaDp(48f)

        val menuWidth = tileSize * 2 + cardGap

        val cardRadius = if (isTablet) figmaDp(14f) else figmaDp(12f)
        val cardShadow = if (isTablet) figmaDp(24f) else figmaDp(14f)

        val documentIconSize = if (isTablet) figmaDp(170f) else figmaDp(86f)
        val dashboardIconSize = if (isTablet) figmaDp(170f) else figmaDp(86f)
        val logoutIconSize = if (isTablet) figmaDp(150f) else figmaDp(78f)

        val labelSize = if (isTablet) figmaSp(38f) else figmaSp(21f)
        val labelTopGap = if (isTablet) figmaDp(32f) else figmaDp(18f)

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(titleTopSpace))

            Text(
                text = "Pilih Menu",
                color = Color.White,
                fontSize = titleSize,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(titleToMenuGap))

            Column(
                modifier = Modifier.widthIn(max = menuWidth),
                verticalArrangement = Arrangement.spacedBy(rowGap),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.width(menuWidth),
                    horizontalArrangement = Arrangement.spacedBy(cardGap),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LandingMenuTile(
                        modifier = Modifier.size(tileSize),
                        radius = cardRadius,
                        shadow = cardShadow,
                        label = "Masukan\nData Anak",
                        labelSize = labelSize,
                        labelTopGap = labelTopGap,
                        gradient = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF27C840),
                                Color(0xFF3C8F4C)
                            )
                        ),
                        onClick = onOpenChildForm
                    ) {
                        DocumentMenuIcon(
                            modifier = Modifier.size(documentIconSize)
                        )
                    }

                    LandingMenuTile(
                        modifier = Modifier.size(tileSize),
                        radius = cardRadius,
                        shadow = cardShadow,
                        label = "Dashboard",
                        labelSize = labelSize,
                        labelTopGap = labelTopGap,
                        gradient = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF27C840),
                                Color(0xFF3C8F4C)
                            )
                        ),
                        onClick = onOpenDashboard
                    ) {
                        DashboardMenuIcon(
                            modifier = Modifier.size(dashboardIconSize)
                        )
                    }
                }

                Row(
                    modifier = Modifier.width(menuWidth),
                    horizontalArrangement = Arrangement.spacedBy(cardGap),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LandingMenuTile(
                        modifier = Modifier.size(tileSize),
                        radius = cardRadius,
                        shadow = cardShadow,
                        label = "Masukkan Data\nPemeriksaan",
                        labelSize = labelSize,
                        labelTopGap = labelTopGap,
                        gradient = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF27C840),
                                Color(0xFF3C8F4C)
                            )
                        ),
                        onClick = onOpenExamination
                    ) {
                        DocumentMenuIcon(
                            modifier = Modifier.size(documentIconSize)
                        )
                    }

                    LandingMenuTile(
                        modifier = Modifier.size(tileSize),
                        radius = cardRadius,
                        shadow = cardShadow,
                        label = "Keluar",
                        labelSize = labelSize,
                        labelTopGap = labelTopGap,
                        gradient = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFFF1616),
                                Color(0xFFFF5A55)
                            )
                        ),
                        onClick = onLogout
                    ) {
                        LogoutMenuIcon(
                            modifier = Modifier.size(logoutIconSize)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LandingMenuTile(
    modifier: Modifier = Modifier,
    radius: Dp,
    shadow: Dp,
    label: String,
    labelSize: TextUnit,
    labelTopGap: Dp,
    gradient: Brush,
    onClick: () -> Unit,
    icon: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val shape = RoundedCornerShape(radius)

    Box(
        modifier = modifier
            .shadow(
                elevation = shadow,
                shape = shape,
                ambientColor = Color.Black.copy(alpha = 0.24f),
                spotColor = Color.Black.copy(alpha = 0.24f)
            )
            .clip(shape)
            .background(brush = gradient)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            icon()

            Spacer(modifier = Modifier.height(labelTopGap))

            Text(
                text = label,
                color = Color.White,
                fontSize = labelSize,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                lineHeight = (labelSize.value * 1.18f).sp
            )
        }
    }
}

@Composable
private fun DocumentMenuIcon(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        val documentPath = Path().apply {
            moveTo(w * 0.18f, h * 0.04f)
            lineTo(w * 0.63f, h * 0.04f)
            lineTo(w * 0.84f, h * 0.25f)
            lineTo(w * 0.84f, h * 0.88f)
            quadraticBezierTo(w * 0.84f, h * 0.96f, w * 0.76f, h * 0.96f)
            lineTo(w * 0.18f, h * 0.96f)
            quadraticBezierTo(w * 0.10f, h * 0.96f, w * 0.10f, h * 0.88f)
            lineTo(w * 0.10f, h * 0.12f)
            quadraticBezierTo(w * 0.10f, h * 0.04f, w * 0.18f, h * 0.04f)
            close()
        }

        drawPath(
            path = documentPath,
            color = Color.White
        )

        val foldPath = Path().apply {
            moveTo(w * 0.63f, h * 0.04f)
            lineTo(w * 0.63f, h * 0.25f)
            quadraticBezierTo(w * 0.63f, h * 0.30f, w * 0.68f, h * 0.30f)
            lineTo(w * 0.84f, h * 0.30f)
            close()
        }

        drawPath(
            path = foldPath,
            color = Color(0xFF2DBB55)
        )

        drawLine(
            color = Color(0xFF2FA94F),
            start = Offset(w * 0.30f, h * 0.56f),
            end = Offset(w * 0.66f, h * 0.56f),
            strokeWidth = w * 0.075f,
            cap = StrokeCap.Round
        )

        drawLine(
            color = Color(0xFF2FA94F),
            start = Offset(w * 0.30f, h * 0.72f),
            end = Offset(w * 0.64f, h * 0.72f),
            strokeWidth = w * 0.075f,
            cap = StrokeCap.Round
        )
    }
}

@Composable
private fun DashboardMenuIcon(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val gap = size.width * 0.12f
        val blockW = (size.width - gap) / 2f
        val blockH = (size.height - gap) / 2f
        val radius = CornerRadius(size.width * 0.03f, size.width * 0.03f)

        drawRoundRect(
            color = Color.White,
            topLeft = Offset(0f, 0f),
            size = Size(blockW, blockH),
            cornerRadius = radius
        )

        drawRoundRect(
            color = Color.White,
            topLeft = Offset(blockW + gap, 0f),
            size = Size(blockW, blockH * 0.62f),
            cornerRadius = radius
        )

        drawRoundRect(
            color = Color.White,
            topLeft = Offset(0f, blockH + gap),
            size = Size(blockW, blockH * 0.62f),
            cornerRadius = radius
        )

        drawRoundRect(
            color = Color.White,
            topLeft = Offset(blockW + gap, blockH * 0.72f + gap),
            size = Size(blockW, blockH * 1.10f),
            cornerRadius = radius
        )
    }
}

@Composable
private fun LogoutMenuIcon(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val stroke = size.width * 0.055f

        drawArc(
            color = Color.White,
            startAngle = 90f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(size.width * 0.15f, size.height * 0.12f),
            size = Size(size.width * 0.34f, size.height * 0.76f),
            style = Stroke(
                width = stroke,
                cap = StrokeCap.Round
            )
        )

        drawLine(
            color = Color.White,
            start = Offset(size.width * 0.47f, size.height * 0.50f),
            end = Offset(size.width * 0.86f, size.height * 0.50f),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )

        drawLine(
            color = Color.White,
            start = Offset(size.width * 0.68f, size.height * 0.30f),
            end = Offset(size.width * 0.86f, size.height * 0.50f),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )

        drawLine(
            color = Color.White,
            start = Offset(size.width * 0.68f, size.height * 0.70f),
            end = Offset(size.width * 0.86f, size.height * 0.50f),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
    }
}