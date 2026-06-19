package com.pantausikecil.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pantausikecil.model.GrowthStatus
import com.pantausikecil.ui.theme.BlueAction
import com.pantausikecil.ui.theme.DarkGreen
import com.pantausikecil.ui.theme.PrimaryGreen
import com.pantausikecil.ui.theme.RedStatus
import com.pantausikecil.ui.theme.SoftGreen
import com.pantausikecil.ui.theme.SoftGrey
import com.pantausikecil.ui.theme.YellowStatus

const val FIGMA_TO_COMPOSE_SCALE = 1.5f
fun figmaDp(px: Float): Dp = (px / FIGMA_TO_COMPOSE_SCALE).dp
fun figmaSp(px: Float): TextUnit = (px / FIGMA_TO_COMPOSE_SCALE).sp

val DashboardHeaderGreen = Color(0xFF46B86A)
val DashboardHeaderGreenDark = Color(0xFF378B4E)
val DashboardPrimaryGreen = Color(0xFF27C840)
val DashboardPrimaryGreenDark = Color(0xFF506E58)
val DashboardDarkGreen = Color(0xFF236D3A)
val DashboardBlue = Color(0xFF2387E9)
val DashboardRed = Color(0xFFFF5A55)
val DashboardRedStatus = Color(0xFFFF5D66)
val DashboardOrange = Color(0xFFFFA800)
val DashboardOrangeDark = Color(0xFFFF6400)
val DashboardGreyText = Color(0xFF696969)

@Composable
fun GreenGradientBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        PrimaryGreen,
                        Color(0xFFA9E5B8),
                        Color(0xFFEAF8EE)
                    )
                )
            )
    ) {
        content()
    }
}

@Composable
fun BackHeader(
    title: String? = null,
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
        }
        if (title != null) {
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.width(48.dp))
        } else {
            Text(text = "Kembali", fontSize = 13.sp)
        }
    }
}

@Composable
fun PrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: ImageVector? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(46.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
    ) {
        if (icon != null) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(text = text, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun BlueButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(40.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = BlueAction)
    ) {
        Text(text = text, fontWeight = FontWeight.Bold, fontSize = 12.sp)
    }
}

@Composable
fun DangerButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(40.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = RedStatus)
    ) {
        Text(text = text, fontWeight = FontWeight.Bold, fontSize = 12.sp)
    }
}

@Composable
fun PantauTextField(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    leadingIcon: ImageVector? = null,
    trailingText: String? = null,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        label = { Text(label) },
        placeholder = { if (placeholder.isNotBlank()) Text(placeholder) },
        singleLine = true,
        leadingIcon = if (leadingIcon != null) {
            { Icon(leadingIcon, contentDescription = null) }
        } else null,
        trailingIcon = if (trailingText != null) {
            { Text(trailingText, modifier = Modifier.padding(end = 8.dp), fontSize = 12.sp) }
        } else null,
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    PantauTextField(
        value = value,
        label = "",
        placeholder = "Cari",
        leadingIcon = Icons.Default.Search,
        modifier = modifier,
        onValueChange = onValueChange
    )
}

@Composable
fun StatusChip(status: GrowthStatus) {
    val color = statusColor(status)
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50.dp))
            .background(color.copy(alpha = 0.12f))
            .border(1.dp, color.copy(alpha = 0.4f), RoundedCornerShape(50.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = status.label,
            color = color,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

fun statusColor(status: GrowthStatus): Color {
    return when (status) {
        GrowthStatus.Normal -> PrimaryGreen
        GrowthStatus.Risk -> YellowStatus
        GrowthStatus.Stunting -> RedStatus
        GrowthStatus.GiziKurang -> Color(0xFFFF8A00)
        GrowthStatus.GiziCukup -> DarkGreen
    }
}

@Composable
fun WhiteCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(modifier = Modifier.padding(14.dp)) {
            content()
        }
    }
}

@Composable
fun MenuTile(
    title: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(110.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(42.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun SummaryBox(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(86.dp),
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = value,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun MiniSummaryBox(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = value, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = title, color = Color.White, fontSize = 10.sp)
        }
    }
}

@Composable
fun CircleIcon(
    icon: ImageVector,
    tint: Color = PrimaryGreen
) {
    Box(
        modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(SoftGreen),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = null, tint = tint)
    }
}

@Composable
fun PlaceholderLineChart(modifier: Modifier = Modifier) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(SoftGrey, RoundedCornerShape(10.dp))
            .padding(8.dp)
    ) {
        val width = size.width
        val height = size.height
        val path = Path().apply {
            moveTo(0f, height * 0.72f)
            cubicTo(width * 0.18f, height * 0.40f, width * 0.30f, height * 0.78f, width * 0.48f, height * 0.50f)
            cubicTo(width * 0.62f, height * 0.28f, width * 0.75f, height * 0.35f, width, height * 0.60f)
        }
        drawPath(path = path, color = PrimaryGreen, style = Stroke(width = 5f, cap = StrokeCap.Round))
    }
}

val IconDocument = Icons.Default.Description
val IconDashboard = Icons.Default.Dashboard
val IconSchedule = Icons.Default.Today
val IconCheck = Icons.Default.Check
val IconEdit = Icons.Default.Edit
val IconDelete = Icons.Default.Delete
val IconSend = Icons.Default.Send
val IconAdd = Icons.Default.Add
val IconClose = Icons.Default.Close
val IconPerson = Icons.Default.Person
val IconWeight = Icons.Default.MonitorWeight
val IconWhatsapp = Icons.Default.Send
