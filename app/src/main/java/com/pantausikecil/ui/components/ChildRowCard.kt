package com.pantausikecil.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pantausikecil.model.Child
import com.pantausikecil.model.GrowthStatus

@Composable
fun ChildRowCard(
    child: Child,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Color(0xFFE0E0E0),
                shape = RoundedCornerShape(14.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 14.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFE8F5EC)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.ChildCare,
                    contentDescription = null,
                    tint = Color(0xFF2E9F4B),
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = child.name,
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = child.parentName,
                    color = Color(0xFF6D6D6D),
                    fontSize = 13.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = child.ageLabel,
                    color = Color(0xFF6D6D6D),
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                ChildStatusBadge(status = child.status)

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = child.gender.label,
                    color = Color(0xFF6D6D6D),
                    fontSize = 12.sp
                )

                Text(
                    text = "Pemeriksaan : ${child.measurements.size}",
                    color = Color(0xFF6D6D6D),
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun ChildStatusBadge(
    status: GrowthStatus
) {
    val background = when (status) {
        GrowthStatus.Normal -> Color(0xFF4CBF73)
        GrowthStatus.GiziCukup -> Color(0xFF27C840)
        GrowthStatus.GiziKurang -> Color(0xFFFF6400)
        GrowthStatus.Risk -> Color(0xFFFFA800)
        GrowthStatus.Stunting -> Color(0xFFFF5D66)
    }

    val text = when (status) {
        GrowthStatus.Normal -> "Normal"
        GrowthStatus.GiziCukup -> "Gizi Cukup"
        GrowthStatus.GiziKurang -> "Gizi Kurang"
        GrowthStatus.Risk -> "Beresiko"
        GrowthStatus.Stunting -> "Stunting"
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(background)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}