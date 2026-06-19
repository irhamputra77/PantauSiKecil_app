package com.pantausikecil.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.pantausikecil.ui.components.figmaDp
import com.pantausikecil.ui.components.figmaSp

private val HeaderGreen = Color(0xFF46B86A)
private val HeaderGreenDark = Color(0xFF378B4E)

@Composable
fun DashboardScaffold(
    title: String,
    onBack: () -> Unit,
    bottomPadding: Dp,
    content: @Composable (isTablet: Boolean) -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val isTablet = maxWidth >= figmaDp(1050f)

        val headerHeight = if (isTablet) figmaDp(360f) else figmaDp(235f)
        val sheetTop = if (isTablet) figmaDp(350f) else figmaDp(225f)
        val sheetRadius = if (isTablet) figmaDp(35f) else figmaDp(28f)

        val titleSize = when {
            isTablet && title.contains("\n") -> figmaSp(76f)
            isTablet -> figmaSp(78f)
            title.contains("\n") -> figmaSp(43f)
            else -> figmaSp(46f)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            HeaderGreen,
                            HeaderGreenDark
                        )
                    )
                )
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Kembali",
                tint = Color.Black,
                modifier = Modifier
                    .padding(
                        start = if (isTablet) figmaDp(30f) else figmaDp(22f),
                        top = if (isTablet) figmaDp(58f) else figmaDp(38f)
                    )
                    .height(if (isTablet) figmaDp(32f) else figmaDp(28f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onBack
                    )
            )

            Text(
                text = title,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = figmaDp(45f)),
                color = Color.White,
                fontSize = titleSize,
                lineHeight = (titleSize.value * 1.18f).sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
        }

        Box(
            modifier = Modifier
                .padding(top = sheetTop)
                .fillMaxSize()
                .clip(
                    RoundedCornerShape(
                        topStart = sheetRadius,
                        topEnd = sheetRadius
                    )
                )
                .background(Color.White)
        )

        Column(
            modifier = Modifier
                .padding(top = sheetTop)
                .fillMaxSize()
                .padding(
                    top = if (isTablet) figmaDp(72f) else figmaDp(44f),
                    bottom = bottomPadding
                )
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .widthIn(
                        max = if (isTablet) figmaDp(1050f) else figmaDp(620f)
                    )
                    .fillMaxWidth(
                        if (isTablet) 0.88f else 0.86f
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content(isTablet)

                Spacer(
                    modifier = Modifier.height(
                        if (isTablet) figmaDp(40f) else figmaDp(24f)
                    )
                )
            }
        }
    }
}