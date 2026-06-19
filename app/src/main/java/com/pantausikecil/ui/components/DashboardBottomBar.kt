package com.pantausikecil.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pantausikecil.ui.screens.dashboard.DashboardTab

@Composable
fun DashboardBottomBar(
    selectedTab: DashboardTab,
    onTabSelected: (DashboardTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        DashboardTab.Home,
        DashboardTab.Schedule,
        DashboardTab.Data,
        DashboardTab.Check
    )

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = figmaDp(28f),
                shape = RoundedCornerShape(999.dp),
                ambientColor = Color.Black.copy(alpha = 0.24f),
                spotColor = Color.Black.copy(alpha = 0.24f)
            )
            .clip(RoundedCornerShape(999.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF45B86A), Color(0xFF2B7D44))
                )
            )
    ) {
        val isTablet = maxWidth >= 600.dp
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, tab ->
                BottomNavTile(
                    tab = tab,
                    index = index,
                    selected = selectedTab == tab,
                    isTablet = isTablet,
                    onClick = { onTabSelected(tab) }
                )
            }
        }
    }
}

@Composable
private fun BottomNavTile(
    tab: DashboardTab,
    index: Int,
    selected: Boolean,
    isTablet: Boolean,
    onClick: () -> Unit
) {
    val color = if (selected) Color.White else Color.White.copy(alpha = 0.72f)

    Column(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = if (isTablet) figmaDp(22f) else figmaDp(10f)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DashboardBottomIcon(
            index = index,
            color = color,
            modifier = Modifier.size(if (isTablet) figmaDp(56f) else figmaDp(32f))
        )

        Spacer(modifier = Modifier.height(if (isTablet) figmaDp(10f) else figmaDp(4f)))

        Text(
            text = tab.label,
            color = color,
            fontSize = if (isTablet) figmaSp(30f) else figmaSp(17f),
            fontWeight = if (selected) FontWeight.ExtraBold else FontWeight.Normal
        )
    }
}
