package com.pantausikecil.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pantausikecil.model.Child
import com.pantausikecil.model.Gender
import com.pantausikecil.model.GrowthStatus

@Composable
fun ChildrenListPanel(
    children: List<Child>,
    isTablet: Boolean,
    onOpenChild: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(
                min = if (isTablet) figmaDp(770f) else figmaDp(430f)
            ),
        shape = RoundedCornerShape(
            if (isTablet) figmaDp(14f) else figmaDp(10f)
        ),
        color = Color.White,
        border = BorderStroke(
            width = 1.5.dp,
            color = Color(0xFF8F8F8F)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = if (isTablet) figmaDp(46f) else figmaDp(22f),
                    end = if (isTablet) figmaDp(46f) else figmaDp(22f),
                    top = if (isTablet) figmaDp(50f) else figmaDp(28f),
                    bottom = if (isTablet) figmaDp(50f) else figmaDp(28f)
                )
        ) {
            if (children.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(
                            if (isTablet) figmaDp(420f) else figmaDp(240f)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Tidak ada data",
                        color = Color(0xFF777777),
                        fontSize = if (isTablet) figmaSp(26f) else figmaSp(16f),
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                children.forEachIndexed { index, child ->
                    ChildDataFigmaRow(
                        child = child,
                        isTablet = isTablet,
                        onClick = {
                            onOpenChild(child.id)
                        }
                    )

                    if (index != children.lastIndex) {
                        Spacer(
                            modifier = Modifier.height(
                                if (isTablet) figmaDp(32f) else figmaDp(18f)
                            )
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color(0xFF9D9D9D))
                        )

                        Spacer(
                            modifier = Modifier.height(
                                if (isTablet) figmaDp(32f) else figmaDp(18f)
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChildDataFigmaRow(
    child: Child,
    isTablet: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = child.name,
                color = Color.Black,
                fontSize = if (isTablet) figmaSp(38f) else figmaSp(22f),
                fontWeight = FontWeight.ExtraBold,
                maxLines = 1
            )

            Spacer(
                modifier = Modifier.height(
                    if (isTablet) figmaDp(8f) else figmaDp(4f)
                )
            )

            Text(
                text = child.parentName.ifBlank { "-" },
                color = Color(0xFF777777),
                fontSize = if (isTablet) figmaSp(27f) else figmaSp(15f),
                fontWeight = FontWeight.Normal,
                maxLines = 1
            )

            Spacer(
                modifier = Modifier.height(
                    if (isTablet) figmaDp(8f) else figmaDp(4f)
                )
            )

            Text(
                text = child.ageLabel.ifBlank { "-" },
                color = Color(0xFF777777),
                fontSize = if (isTablet) figmaSp(27f) else figmaSp(15f),
                fontWeight = FontWeight.Normal,
                maxLines = 1
            )
        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    if (isTablet) 14.dp else 8.dp
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ChildStatusPill(
                    text = nutritionText(child.status),
                    color = nutritionColor(child.status),
                    isTablet = isTablet
                )

                ChildStatusPill(
                    text = stuntingText(child.status),
                    color = stuntingColor(child.status),
                    isTablet = isTablet
                )
            }

            Spacer(
                modifier = Modifier.height(
                    if (isTablet) figmaDp(12f) else figmaDp(6f)
                )
            )

            Text(
                text = genderText(child.gender),
                color = Color(0xFF777777),
                fontSize = if (isTablet) figmaSp(27f) else figmaSp(15f),
                textAlign = TextAlign.End
            )

            Spacer(
                modifier = Modifier.height(
                    if (isTablet) figmaDp(10f) else figmaDp(4f)
                )
            )

            Text(
                text = "Pemeriksaan : ${maxOf(child.examinationCount, child.measurements.size)}",
                color = Color(0xFF777777),
                fontSize = if (isTablet) figmaSp(27f) else figmaSp(15f),
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
private fun ChildStatusPill(
    text: String,
    color: Color,
    isTablet: Boolean
) {
    Box(
        modifier = Modifier
            .shadow(
                elevation = if (isTablet) 12.dp else 6.dp,
                shape = RoundedCornerShape(999.dp),
                clip = false
            )
            .background(
                color = color,
                shape = RoundedCornerShape(999.dp)
            )
            .height(
                if (isTablet) 20.dp else 16.dp
            )
            .widthIn(
                min = if (isTablet) 100.dp else 60.dp
            )
            .padding(
                horizontal = if (isTablet) 8.dp else 2.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = if (isTablet) figmaSp(15f) else figmaSp(10.5f),
            lineHeight = if (isTablet) figmaSp(15f) else figmaSp(10.5f),
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            textAlign = TextAlign.Center,
            style = TextStyle(
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            )
        )
    }
}

private fun nutritionText(status: GrowthStatus): String {
    return when (status) {
        GrowthStatus.Normal,
        GrowthStatus.GiziCukup -> "Gizi Cukup"

        GrowthStatus.Risk,
        GrowthStatus.Stunting,
        GrowthStatus.GiziKurang -> "Gizi Kurang"
    }
}

private fun nutritionColor(status: GrowthStatus): Color {
    return when (status) {
        GrowthStatus.Normal,
        GrowthStatus.GiziCukup -> Color(0xFF27D967)

        GrowthStatus.Risk,
        GrowthStatus.Stunting,
        GrowthStatus.GiziKurang -> Color(0xFFFF6A00)
    }
}

private fun stuntingText(status: GrowthStatus): String {
    return when (status) {
        GrowthStatus.Normal,
        GrowthStatus.GiziCukup -> "Normal"

        GrowthStatus.Risk,
        GrowthStatus.GiziKurang -> "Beresiko"

        GrowthStatus.Stunting -> "Stunting"
    }
}

private fun stuntingColor(status: GrowthStatus): Color {
    return when (status) {
        GrowthStatus.Normal,
        GrowthStatus.GiziCukup -> Color(0xFF43B96A)

        GrowthStatus.Risk,
        GrowthStatus.GiziKurang -> Color(0xFFFFA800)

        GrowthStatus.Stunting -> Color(0xFFFF5F6D)
    }
}

private fun genderText(gender: Gender): String {
    return when (gender) {
        Gender.Male -> "Laki-Laki"
        Gender.Female -> "Perempuan"
    }
}