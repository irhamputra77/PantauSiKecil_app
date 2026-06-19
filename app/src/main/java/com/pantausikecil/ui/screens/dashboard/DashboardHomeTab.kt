package com.pantausikecil.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.pantausikecil.model.Child
import com.pantausikecil.model.GrowthStatus
import com.pantausikecil.model.PosyanduSchedule
import com.pantausikecil.ui.components.DashboardDarkGreen
import com.pantausikecil.ui.components.DashboardTextPill
import com.pantausikecil.ui.components.LargeSummaryCard
import com.pantausikecil.ui.components.LatestScheduleSection
import com.pantausikecil.ui.components.SmallSummaryCard
import com.pantausikecil.ui.components.figmaDp
import com.pantausikecil.ui.components.figmaSp

@Composable
fun DashboardHomeTab(
    children: List<Child>,
    schedules: List<PosyanduSchedule>,
    onBack: () -> Unit,
    bottomPadding: Dp
) {
    val totalAnak = children.size

    val normal = children.count {
        it.status == GrowthStatus.Normal
    }

    val risk = children.count {
        it.status == GrowthStatus.Risk
    }

    val stunting = children.count {
        it.status == GrowthStatus.Stunting
    }

    val kurang = children.count {
        it.status == GrowthStatus.GiziKurang
    }

    val cukup = children.count {
        it.status == GrowthStatus.GiziCukup || it.status == GrowthStatus.Normal
    }

    DashboardScaffold(
        title = "Selamat Datang\nDi Dashboard",
        onBack = onBack,
        bottomPadding = bottomPadding
    ) { isTablet ->
        val gap = if (isTablet) {
            figmaDp(18f)
        } else {
            figmaDp(12f)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DashboardTextPill(
                text = "Posyandu Melati",
                background = DashboardDarkGreen,
                textColor = Color.White,
                isTablet = isTablet,
                modifier = Modifier.width(
                    if (isTablet) {
                        figmaDp(265f)
                    } else {
                        figmaDp(160f)
                    }
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "📍 Sukabirus, Bojongsoang, Bandung, Jawa Barat",
                color = Color.Black,
                fontSize = if (isTablet) {
                    figmaSp(24f)
                } else {
                    figmaSp(13f)
                },
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(
            modifier = Modifier.height(
                if (isTablet) {
                    figmaDp(82f)
                } else {
                    figmaDp(42f)
                }
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(gap)
        ) {
            LargeSummaryCard(
                title = "Data Anak",
                value = totalAnak.toString(),
                gradient = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFC43E),
                        Color(0xFFF2A600)
                    )
                ),
                modifier = Modifier.weight(1f),
                isTablet = isTablet
            )

            LargeSummaryCard(
                title = "Normal",
                value = normal.toString(),
                gradient = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF27C840),
                        Color(0xFF0D6924)
                    )
                ),
                modifier = Modifier.weight(1f),
                isTablet = isTablet
            )
        }

        Spacer(
            modifier = Modifier.height(
                if (isTablet) {
                    figmaDp(50f)
                } else {
                    figmaDp(24f)
                }
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(gap)
        ) {
            SmallSummaryCard(
                label = "Beresiko Stunting",
                value = risk.toString(),
                gradient = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFFF6500),
                        Color(0xFFD05A00)
                    )
                ),
                modifier = Modifier.weight(1f),
                isTablet = isTablet
            )

            SmallSummaryCard(
                label = "Stunting",
                value = stunting.toString(),
                gradient = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFFF1313),
                        Color(0xFFC90404)
                    )
                ),
                modifier = Modifier.weight(1f),
                isTablet = isTablet
            )
        }

        Spacer(
            modifier = Modifier.height(
                if (isTablet) {
                    figmaDp(38f)
                } else {
                    figmaDp(18f)
                }
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(gap)
        ) {
            SmallSummaryCard(
                label = "Gizi Kurang",
                value = kurang.toString(),
                gradient = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFE6A300),
                        Color(0xFFB88300)
                    )
                ),
                modifier = Modifier.weight(1f),
                isTablet = isTablet
            )

            SmallSummaryCard(
                label = "Gizi Cukup",
                value = cukup.toString(),
                gradient = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF4CBF73),
                        Color(0xFF328A4E)
                    )
                ),
                modifier = Modifier.weight(1f),
                isTablet = isTablet
            )
        }

        Spacer(
            modifier = Modifier.height(
                if (isTablet) {
                    figmaDp(72f)
                } else {
                    figmaDp(36f)
                }
            )
        )

        LatestScheduleSection(
            schedule = schedules.firstOrNull(),
            isTablet = isTablet
        )
    }
}