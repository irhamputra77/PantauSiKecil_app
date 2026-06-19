package com.pantausikecil.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pantausikecil.R
import com.pantausikecil.model.PosyanduSchedule

@Composable
fun ScheduleCard(
    schedule: PosyanduSchedule,
    modifier: Modifier = Modifier,
    isTablet: Boolean,
    onSend: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val cardRadius = if (isTablet) 16.dp else 8.dp
    val cardBorder = if (isTablet) 1.4.dp else 1.dp

    val horizontalPadding = if (isTablet) 30.dp else 10.dp
    val verticalPadding = if (isTablet) 26.dp else 10.dp

    val titleSize = if (isTablet) 26.sp else 12.sp
    val metaSize = if (isTablet) 17.sp else 8.sp
    val activitySize = if (isTablet) 19.sp else 11.sp

    val metaIconSize = if (isTablet) 24.dp else 10.dp
    val whatsappIconSize = if (isTablet) 27.dp else 13.dp

    val whatsappButtonHeight = if (isTablet) 46.dp else 25.dp
    val smallButtonHeight = if (isTablet) 44.dp else 22.dp

    val whatsappButtonTextSize = if (isTablet) 19.sp else 9.sp
    val smallButtonTextSize = if (isTablet) 18.sp else 9.sp

    val buttonRadius = if (isTablet) 10.dp else 5.dp

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(490f / 475f)
            .border(
                width = cardBorder,
                color = Color(0xFF8A8A8A),
                shape = RoundedCornerShape(cardRadius)
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(cardRadius)
            )
            .padding(
                horizontal = horizontalPadding,
                vertical = verticalPadding
            )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = schedule.title,
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF222222),
                fontSize = titleSize,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(
                modifier = Modifier.height(
                    if (isTablet) 18.dp else 1.dp
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ScheduleMeta(
                    icon = Icons.Filled.Today,
                    text = schedule.date,
                    iconSize = metaIconSize,
                    fontSize = metaSize
                )

                Spacer(
                    modifier = Modifier.width(
                        if (isTablet) 38.dp else 10.dp
                    )
                )

                ScheduleMeta(
                    icon = Icons.Filled.Schedule,
                    text = schedule.time,
                    iconSize = metaIconSize,
                    fontSize = metaSize
                )
            }

            Spacer(
                modifier = Modifier.height(
                    if (isTablet) 44.dp else 4.dp
                )
            )

            Text(
                text = schedule.activity,
                modifier = Modifier.fillMaxWidth(),
                color = Color.Black,
                fontSize = activitySize,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.weight(1f).height(
                4.dp
            ))

            ScheduleWhatsappButton(
                text = "Kirim WhatsApp",
                height = whatsappButtonHeight,
                radius = buttonRadius,
                fontSize = whatsappButtonTextSize,
                iconSize = whatsappIconSize,
                modifier = Modifier.fillMaxWidth(),
                onClick = onSend
            )

            Spacer(
                modifier = Modifier.height(
                    if (isTablet) 10.dp else 6.dp
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    if (isTablet) 10.dp else 6.dp
                )
            ) {
                ScheduleSolidButton(
                    text = "Edit Jadwal",
                    background = Color(0xFF2387E9),
                    height = smallButtonHeight,
                    radius = buttonRadius,
                    fontSize = smallButtonTextSize,
                    modifier = Modifier.weight(1f),
                    onClick = onEdit
                )

                ScheduleSolidButton(
                    text = "Hapus",
                    background = Color(0xFFFF5A55),
                    height = smallButtonHeight,
                    radius = buttonRadius,
                    fontSize = smallButtonTextSize,
                    modifier = Modifier.weight(1f),
                    onClick = onDelete
                )
            }
        }
    }
}

@Composable
private fun ScheduleMeta(
    icon: ImageVector,
    text: String,
    iconSize: androidx.compose.ui.unit.Dp,
    fontSize: androidx.compose.ui.unit.TextUnit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF5E5E5E),
            modifier = Modifier.size(iconSize)
        )

        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text = text,
            color = Color(0xFF5E5E5E),
            fontSize = fontSize,
            fontWeight = FontWeight.Normal,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun ScheduleWhatsappButton(
    text: String,
    height: androidx.compose.ui.unit.Dp,
    radius: androidx.compose.ui.unit.Dp,
    fontSize: androidx.compose.ui.unit.TextUnit,
    iconSize: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .height(height)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(radius),
                ambientColor = Color.Black.copy(alpha = 0.16f),
                spotColor = Color.Black.copy(alpha = 0.16f)
            )
            .clip(RoundedCornerShape(radius))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF27C840),
                        Color(0xFF2BD365)
                    )
                )
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = fontSize,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                maxLines = 1
            )

            Spacer(modifier = Modifier.width(6.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_whatsapp_custom),
                contentDescription = "WhatsApp",
                modifier = Modifier.size(iconSize),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
private fun ScheduleSolidButton(
    text: String,
    background: Color,
    height: androidx.compose.ui.unit.Dp,
    radius: androidx.compose.ui.unit.Dp,
    fontSize: androidx.compose.ui.unit.TextUnit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .height(height)
            .clip(RoundedCornerShape(radius))
            .background(background)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = fontSize,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}