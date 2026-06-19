package com.pantausikecil.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun DashboardBottomIcon(
    index: Int,
    color: Color,
    modifier: Modifier
) {
    Canvas(modifier = modifier) {
        when (index) {
            0 -> {
                val gap = size.width * 0.12f
                val block = (size.width - gap) / 2f
                drawRoundRect(color, Offset(0f, 0f), Size(block, block), CornerRadius(3f, 3f))
                drawRoundRect(color, Offset(block + gap, 0f), Size(block, block), CornerRadius(3f, 3f))
                drawRoundRect(color, Offset(0f, block + gap), Size(block, block), CornerRadius(3f, 3f))
                drawRoundRect(color, Offset(block + gap, block + gap), Size(block, block), CornerRadius(3f, 3f))
            }

            1 -> {
                val stroke = size.width * 0.08f
                drawRoundRect(
                    color = color,
                    topLeft = Offset(size.width * 0.12f, size.height * 0.20f),
                    size = Size(size.width * 0.76f, size.height * 0.68f),
                    cornerRadius = CornerRadius(size.width * 0.08f, size.width * 0.08f),
                    style = Stroke(stroke)
                )
                drawLine(color, Offset(size.width * 0.25f, size.height * 0.08f), Offset(size.width * 0.25f, size.height * 0.28f), stroke, StrokeCap.Round)
                drawLine(color, Offset(size.width * 0.75f, size.height * 0.08f), Offset(size.width * 0.75f, size.height * 0.28f), stroke, StrokeCap.Round)
                drawLine(color, Offset(size.width * 0.12f, size.height * 0.42f), Offset(size.width * 0.88f, size.height * 0.42f), stroke)
            }

            2 -> {
                val h = size.height / 5f
                repeat(4) { i ->
                    drawOval(
                        color = color,
                        topLeft = Offset(size.width * 0.14f, i * h),
                        size = Size(size.width * 0.72f, h * 1.4f)
                    )
                    if (i < 3) {
                        drawRect(
                            color = color,
                            topLeft = Offset(size.width * 0.14f, i * h + h * 0.7f),
                            size = Size(size.width * 0.72f, h)
                        )
                    }
                }
            }

            3 -> {
                val stroke = size.width * 0.08f
                drawRoundRect(
                    color = color,
                    topLeft = Offset(size.width * 0.08f, size.height * 0.18f),
                    size = Size(size.width * 0.78f, size.height * 0.64f),
                    cornerRadius = CornerRadius(size.width * 0.05f, size.width * 0.05f),
                    style = Stroke(stroke)
                )
                drawLine(color, Offset(size.width * 0.36f, size.height * 0.50f), Offset(size.width * 0.82f, size.height * 0.50f), stroke, StrokeCap.Round)
                drawLine(color, Offset(size.width * 0.62f, size.height * 0.32f), Offset(size.width * 0.82f, size.height * 0.50f), stroke, StrokeCap.Round)
                drawLine(color, Offset(size.width * 0.62f, size.height * 0.68f), Offset(size.width * 0.82f, size.height * 0.50f), stroke, StrokeCap.Round)
            }
        }
    }
}
