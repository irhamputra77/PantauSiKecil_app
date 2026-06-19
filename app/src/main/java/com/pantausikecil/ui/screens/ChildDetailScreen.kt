package com.pantausikecil.ui.screens

import android.graphics.Color as AndroidColor
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.pantausikecil.model.Child
import com.pantausikecil.model.Gender
import com.pantausikecil.model.GrowthStatus
import com.pantausikecil.model.Measurement
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.roundToInt

private val DetailGreen = Color(0xFF46B86A)
private val DetailGreenDark = Color(0xFF2F8E4E)
private val DetailPrimaryGreen = Color(0xFF27D967)
private val DetailDarkGreen = Color(0xFF43B96A)
private val DetailBlue = Color(0xFF2687F5)
private val DetailRed = Color(0xFFEF626C)
private val DetailTextDark = Color(0xFF111111)
private val DetailTextGrey = Color(0xFF777777)
private val DetailLineGrey = Color(0xFFD0D0D0)

@Composable
fun ChildDetailScreen(
    child: Child,
    onBack: () -> Unit,
    onSaveMeasurement: (String, Measurement) -> Unit,
    onEditChild: (String) -> Unit = {},
    onDeleteMeasurement: (String, Measurement) -> Unit = { _, _ -> }
) {
    var inputOpen by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf<Measurement?>(null) }
    var editing by remember { mutableStateOf<Measurement?>(null) }
    var deleting by remember { mutableStateOf<Measurement?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    var selectedChart by remember { mutableStateOf("Tinggi") }

    val latestMeasurement = child.measurements.firstOrNull()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DetailGreen)
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Kembali",
            tint = Color.Black,
            modifier = Modifier
                .padding(start = 18.dp, top = 18.dp)
                .size(28.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onBack
                )
        )

        LazyColumn(
            modifier = Modifier
                .padding(top = 48.dp)
                .fillMaxSize()
                .clip(
                    RoundedCornerShape(
                        topStart = 14.dp,
                        topEnd = 14.dp
                    )
                )
                .background(Color.White),
            contentPadding = PaddingValues(
                start = 32.dp,
                end = 32.dp,
                top = 52.dp,
                bottom = 128.dp
            ),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                ChildIdentitySection(
                    child = child,
                    onEditClick = {
                        onEditChild(child.id)
                    }
                )
            }

            item {
                ChildInformationCard(
                    child = child
                )
            }

            item {
                LatestMeasurementCard(
                    measurement = latestMeasurement
                )
            }

            item {
                GrowthChartCard(
                    measurements = child.measurements,
                    selectedChart = selectedChart,
                    onSelectedChartChange = {
                        selectedChart = it
                    }
                )
            }

            item {
                Text(
                    text = "Riwayat Pemeriksaan",
                    modifier = Modifier.fillMaxWidth(),
                    color = DetailTextDark,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
            }

            if (child.measurements.isEmpty()) {
                item {
                    EmptyHistoryCard()
                }
            } else {
                items(child.measurements) { measurement ->
                    MeasurementHistoryCard(
                        measurement = measurement,
                        onSend = {
                            successMessage = "Data Berhasil Terkirim"
                        },
                        onEdit = {
                            editing = measurement
                        },
                        onDelete = {
                            deleting = measurement
                        }
                    )
                }
            }
        }
    }

    if (inputOpen) {
        InputExaminationDialog(
            childId = child.id,
            onDismiss = {
                inputOpen = false
            },
            onResult = { measurement ->
                inputOpen = false
                result = measurement
            }
        )
    }

    result?.let { measurement ->
        ResultExaminationDialog(
            measurement = measurement,
            onSave = {
                onSaveMeasurement(child.id, measurement)
                result = null
                successMessage = "Data Pemeriksaan Tersimpan"
            },
            onSendWhatsapp = {
                onSaveMeasurement(child.id, measurement)
                result = null
                successMessage = "Pemeriksaan Berhasil Terkirim"
            },
            onDismiss = {
                result = null
            }
        )
    }

    editing?.let { measurement ->
        EditExaminationDialog(
            measurement = measurement,
            onDismiss = {
                editing = null
            },
            onSave = { updated ->
                onSaveMeasurement(child.id, updated)
                editing = null
                successMessage = "Data Berhasil di Edit"
            }
        )
    }

    deleting?.let { measurement ->
        ConfirmationDialog(
            message = "Apakah anda yakin\ningin menghapus?",
            positiveText = "Hapus",
            positiveColor = DetailRed,
            onCancel = {
                deleting = null
            },
            onConfirm = {
                onDeleteMeasurement(child.id, measurement)
                deleting = null
                successMessage = "Data Berhasil Dihapus"
            }
        )
    }

    successMessage?.let { message ->
        SuccessDialog(
            message = message,
            onBackToMenu = {
                successMessage = null
            }
        )
    }
}

@Composable
private fun ChildIdentitySection(
    child: Child,
    onEditClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = child.name,
                modifier = Modifier.weight(1f),
                color = DetailTextDark,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                maxLines = 1
            )

            SmallActionButton(
                text = "Edit",
                color = DetailBlue,
                modifier = Modifier
                    .width(104.dp)
                    .height(36.dp),
                onClick = onEditClick
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        IdentityLine(
            icon = Icons.Default.Person,
            label = "Orang Tua : ",
            value = child.parentName.ifBlank { "-" }
        )

        Spacer(modifier = Modifier.height(10.dp))

        IdentityLine(
            icon = Icons.Default.Phone,
            label = "No. Telepon : ",
            value = child.phone.ifBlank { "-" }
        )

        Spacer(modifier = Modifier.height(10.dp))

        IdentityLine(
            icon = Icons.Default.Home,
            label = "Alamat : ",
            value = child.address.ifBlank { "-" }
        )
    }
}

@Composable
private fun IdentityLine(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = DetailTextDark,
            modifier = Modifier.size(17.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        color = DetailTextDark,
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append(label)
                }

                withStyle(
                    SpanStyle(
                        color = DetailTextGrey,
                        fontWeight = FontWeight.Normal
                    )
                ) {
                    append(value)
                }
            },
            fontSize = 14.sp,
            lineHeight = 18.sp
        )
    }
}

@Composable
private fun ChildInformationCard(
    child: Child
) {
    DetailCard {
        Text(
            text = "Informasi Anak",
            modifier = Modifier.fillMaxWidth(),
            color = DetailTextDark,
            fontSize = 21.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        DetailInfoRow(
            label = "Usia",
            value = child.ageLabel.ifBlank { "-" },
            showDivider = true
        )

        DetailInfoRow(
            label = "Jenis Kelamin",
            value = genderLabel(child.gender),
            showDivider = true
        )

        DetailInfoRow(
            label = "Tempat, Tanggal Lahir",
            value = "${child.birthPlace.ifBlank { "-" }}, ${formatDateToIndonesian(child.birthDate)}",
            showDivider = false
        )
    }
}

@Composable
private fun LatestMeasurementCard(
    measurement: Measurement?
) {
    DetailCard {
        Text(
            text = "Pemeriksaan Terakhir",
            modifier = Modifier.fillMaxWidth(),
            color = DetailTextDark,
            fontSize = 21.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(18.dp))

        if (measurement == null) {
            Text(
                text = "Belum ada data pemeriksaan",
                modifier = Modifier.fillMaxWidth(),
                color = DetailTextGrey,
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )
        } else {
            DetailInfoRow(
                label = "Tanggal",
                value = formatDateToIndonesian(measurement.date),
                showDivider = true
            )

            DetailInfoRow(
                label = "Tinggi",
                value = "${formatNumber(measurement.heightCm)} cm",
                showDivider = true
            )

            DetailInfoRow(
                label = "Berat Badan",
                value = "${formatNumber(measurement.weightKg)} kg",
                showDivider = true
            )

            DetailInfoRow(
                label = "Lingkar Kepala",
                value = "${formatNumber(measurement.headCircumferenceCm)} cm",
                showDivider = true
            )

            DetailInfoRow(
                label = "Lingkar Lengan Atas",
                value = "${formatNumber(measurement.armCircumferenceCm)} cm",
                showDivider = true
            )

            DetailInfoRow(
                label = "Cara Ukur",
                value = measurement.measureMethod.ifBlank { "-" },
                showDivider = true
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Status",
                    modifier = Modifier.weight(1f),
                    color = DetailTextDark,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatusPill(
                        text = nutritionText(measurement.status),
                        color = nutritionColor(measurement.status),
                        width = 82.dp,
                        height = 26.dp,
                        fontSize = 10.sp
                    )

                    StatusPill(
                        text = stuntingText(measurement.status),
                        color = stuntingColor(measurement.status),
                        width = 72.dp,
                        height = 26.dp,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailInfoRow(
    label: String,
    value: String,
    showDivider: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            color = DetailTextDark,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal
        )

        Text(
            text = value,
            modifier = Modifier.weight(1f),
            color = DetailTextDark,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.End,
            lineHeight = 19.sp
        )
    }

    if (showDivider) {
        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(DetailLineGrey)
        )

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun GrowthChartCard(
    measurements: List<Measurement>,
    selectedChart: String,
    onSelectedChartChange: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(2.dp),
                clip = false
            )
            .border(
                width = 1.dp,
                color = Color(0xFFE0E0E0),
                shape = RoundedCornerShape(2.dp)
            ),
        color = Color.White,
        shape = RoundedCornerShape(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 24.dp,
                    bottom = 28.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = chartTitle(selectedChart),
                color = DetailTextDark,
                fontSize = 21.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(22.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ChartTabButton(
                    text = "Tinggi",
                    selected = selectedChart == "Tinggi",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onSelectedChartChange("Tinggi")
                    }
                )

                ChartTabButton(
                    text = "Lingkar Kepala",
                    selected = selectedChart == "Lingkar Kepala",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onSelectedChartChange("Lingkar Kepala")
                    }
                )

                ChartTabButton(
                    text = "Lingkar Lengan",
                    selected = selectedChart == "Lingkar Lengan",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        onSelectedChartChange("Lingkar Lengan")
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            MeasurementLineChart(
                measurements = measurements,
                selectedChart = selectedChart,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            )
        }
    }
}

@Composable
private fun MeasurementLineChart(
    measurements: List<Measurement>,
    selectedChart: String,
    modifier: Modifier = Modifier
) {
    val chartMeasurements = remember(measurements) {
        measurements
            .sortedBy { parseDateMillisForChart(it.date) }
            .takeLast(12)
    }

    if (chartMeasurements.isEmpty()) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Belum ada data pemeriksaan",
                color = DetailTextGrey,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
        return
    }

    val entries = remember(chartMeasurements, selectedChart) {
        chartMeasurements.mapIndexed { index, measurement ->
            val value = when (selectedChart) {
                "Tinggi" -> measurement.heightCm
                "Lingkar Kepala" -> measurement.headCircumferenceCm
                "Lingkar Lengan" -> measurement.armCircumferenceCm
                else -> measurement.heightCm
            }

            Entry(index.toFloat(), value.toFloat())
        }
    }

    val labels = remember(chartMeasurements) {
        chartMeasurements.map { measurement ->
            shortDateLabel(measurement.date)
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            LineChart(context).apply {
                description.isEnabled = false
                legend.isEnabled = false
                setTouchEnabled(true)
                setPinchZoom(false)
                setScaleEnabled(false)
                setDrawGridBackground(false)
                setNoDataText("Belum ada data pemeriksaan")

                axisRight.isEnabled = false

                axisLeft.apply {
                    textColor = AndroidColor.rgb(120, 120, 120)
                    gridColor = AndroidColor.rgb(230, 230, 230)
                    axisLineColor = AndroidColor.rgb(210, 210, 210)
                    textSize = 10f
                }

                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    textColor = AndroidColor.rgb(120, 120, 120)
                    gridColor = AndroidColor.TRANSPARENT
                    axisLineColor = AndroidColor.rgb(210, 210, 210)
                    granularity = 1f
                    textSize = 10f
                    setDrawGridLines(false)
                }
            }
        },
        update = { chart ->
            val dataSet = LineDataSet(entries, selectedChart).apply {
                color = AndroidColor.rgb(38, 135, 245)
                setCircleColor(AndroidColor.rgb(38, 135, 245))
                lineWidth = 2.5f
                circleRadius = 4f
                setDrawCircleHole(false)
                setDrawValues(false)
                mode = LineDataSet.Mode.CUBIC_BEZIER
                setDrawFilled(true)
                fillAlpha = 70
                fillColor = AndroidColor.rgb(38, 135, 245)
            }

            chart.xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    val index = value.roundToInt()
                    return labels.getOrNull(index).orEmpty()
                }
            }

            chart.data = LineData(dataSet)
            chart.invalidate()
        }
    )
}

@Composable
private fun MeasurementHistoryCard(
    measurement: Measurement,
    onSend: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = Color.White,
        border = BorderStroke(
            width = 1.dp,
            color = Color(0xFFBDBDBD)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = null,
                    tint = DetailTextDark,
                    modifier = Modifier.size(22.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = formatDateToIndonesian(measurement.date),
                    color = DetailTextDark,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    HistorySmallField(
                        icon = Icons.Default.Height,
                        label = "Tinggi Badan",
                        value = "${formatNumber(measurement.heightCm)} cm"
                    )

                    HistorySmallField(
                        icon = Icons.Default.MonitorWeight,
                        label = "Berat Badan",
                        value = "${formatNumber(measurement.weightKg)} kg"
                    )

                    HistorySmallField(
                        icon = Icons.Default.Straighten,
                        label = "Cara Ukur",
                        value = measurement.measureMethod.ifBlank { "-" }
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    HistorySmallField(
                        icon = Icons.Default.Person,
                        label = "Lingkar Kepala",
                        value = "${formatNumber(measurement.headCircumferenceCm)} cm"
                    )

                    HistorySmallField(
                        icon = Icons.Default.Send,
                        label = "Lingkar Lengan Atas",
                        value = "${formatNumber(measurement.armCircumferenceCm)} cm"
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Status :",
                            color = DetailTextDark,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            StatusPill(
                                text = nutritionText(measurement.status),
                                color = nutritionColor(measurement.status),
                                width = 88.dp,
                                height = 28.dp,
                                fontSize = 10.sp,
                                modifier = Modifier.weight(1f)
                            )

                            StatusPill(
                                text = stuntingText(measurement.status),
                                color = stuntingColor(measurement.status),
                                width = 88.dp,
                                height = 28.dp,
                                fontSize = 10.sp,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                SmallActionButton(
                    text = "Kirim",
                    color = DetailPrimaryGreen,
                    modifier = Modifier
                        .weight(1f)
                        .height(34.dp),
                    onClick = onSend
                )

                SmallActionButton(
                    text = "Edit",
                    color = DetailBlue,
                    modifier = Modifier
                        .weight(1f)
                        .height(34.dp),
                    onClick = onEdit
                )

                SmallActionButton(
                    text = "Hapus",
                    color = DetailRed,
                    modifier = Modifier
                        .weight(1f)
                        .height(34.dp),
                    onClick = onDelete
                )
            }
        }
    }
}

@Composable
private fun EmptyHistoryCard() {
    DetailCard {
        Text(
            text = "Belum ada riwayat pemeriksaan",
            modifier = Modifier.fillMaxWidth(),
            color = DetailTextGrey,
            fontSize = 15.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun HistorySmallField(
    icon: ImageVector,
    label: String,
    value: String
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(6.dp),
        color = Color.White,
        border = BorderStroke(
            width = 1.dp,
            color = Color(0xFFE0E0E0)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF7A7A7A),
                modifier = Modifier.size(18.dp)
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = label,
                modifier = Modifier.weight(1f),
                color = DetailTextDark,
                fontSize = 10.sp,
                maxLines = 1
            )

            Text(
                text = value,
                color = Color(0xFF9A9A9A),
                fontSize = 10.sp,
                maxLines = 1,
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
private fun DetailCard(
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(6.dp),
                clip = false
            ),
        color = Color.White,
        shape = RoundedCornerShape(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 22.dp,
                    bottom = 24.dp
                ),
            content = content
        )
    }
}

@Composable
private fun StatusPill(
    text: String,
    color: Color,
    width: Dp,
    height: Dp,
    fontSize: TextUnit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(999.dp),
                clip = false
            )
            .background(
                color = color,
                shape = RoundedCornerShape(999.dp)
            )
            .widthIn(min = width)
            .height(height)
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = fontSize,
            lineHeight = fontSize,
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

@Composable
private fun ChartTabButton(
    text: String,
    selected: Boolean,
    modifier: Modifier,
    onClick: () -> Unit
) {
    val backgroundColor = if (selected) DetailDarkGreen else Color.White
    val textColor = if (selected) Color.White else DetailDarkGreen

    Box(
        modifier = modifier
            .height(24.dp)
            .border(
                width = 1.5.dp,
                color = DetailDarkGreen,
                shape = RoundedCornerShape(999.dp)
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(999.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 9.sp,
            lineHeight = 9.sp,
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

@Composable
private fun SmallActionButton(
    text: String,
    color: Color,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(
                color = color,
                shape = RoundedCornerShape(6.dp)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 12.sp,
            lineHeight = 12.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            style = TextStyle(
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            )
        )
    }
}

@Composable
private fun DetailBottomItem(
    iconText: String,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(72.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = iconText,
            color = if (selected) Color.White else Color.White.copy(alpha = 0.72f),
            fontSize = 30.sp,
            lineHeight = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = label,
            color = if (selected) Color.White else Color.White.copy(alpha = 0.72f),
            fontSize = 15.sp,
            lineHeight = 15.sp,
            fontWeight = if (selected) FontWeight.ExtraBold else FontWeight.Normal,
            textAlign = TextAlign.Center
        )
    }
}

private fun genderLabel(gender: Gender): String {
    return when (gender) {
        Gender.Male -> "Laki - Laki"
        Gender.Female -> "Perempuan"
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
        GrowthStatus.GiziCukup -> DetailPrimaryGreen

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
        GrowthStatus.GiziCukup -> DetailDarkGreen

        GrowthStatus.Risk,
        GrowthStatus.GiziKurang -> Color(0xFFFFA800)

        GrowthStatus.Stunting -> DetailRed
    }
}

private fun formatNumber(value: Double): String {
    return if (value % 1.0 == 0.0) {
        value.toInt().toString()
    } else {
        String.format(Locale("id", "ID"), "%.1f", value)
    }
}

private fun formatDateToIndonesian(value: String): String {
    if (value.isBlank()) return "-"

    val locale = Locale("id", "ID")
    val output = SimpleDateFormat("d MMMM yyyy", locale)

    val patterns = listOf(
        "yyyy-MM-dd",
        "dd MMM yyyy",
        "dd MMMM yyyy",
        "d MMMM yyyy",
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        "yyyy-MM-dd'T'HH:mm:ss'Z'"
    )

    for (pattern in patterns) {
        try {
            val parser = SimpleDateFormat(pattern, locale)
            parser.isLenient = false
            val date = parser.parse(value)
            if (date != null) {
                return output.format(date)
            }
        } catch (_: Exception) {
        }
    }

    return value
}

private fun chartTitle(selectedChart: String): String {
    return when (selectedChart) {
        "Tinggi" -> "Grafik Tinggi"
        "Lingkar Kepala" -> "Grafik Lingkar Kepala"
        "Lingkar Lengan" -> "Grafik Lingkar Lengan"
        else -> "Grafik Pemeriksaan"
    }
}

private fun shortDateLabel(value: String): String {
    if (value.isBlank()) return "-"

    val locale = Locale("id", "ID")
    val output = SimpleDateFormat("dd/MM", locale)

    val patterns = listOf(
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        "yyyy-MM-dd'T'HH:mm:ss'Z'",
        "yyyy-MM-dd",
        "dd MMM yyyy",
        "dd MMMM yyyy",
        "d MMMM yyyy"
    )

    for (pattern in patterns) {
        try {
            val parser = SimpleDateFormat(pattern, locale)
            parser.isLenient = false
            val date = parser.parse(value)
            if (date != null) {
                return output.format(date)
            }
        } catch (_: Exception) {
        }
    }

    return value.take(5)
}

private fun parseDateMillisForChart(value: String): Long {
    if (value.isBlank()) return 0L

    val locale = Locale("id", "ID")

    val patterns = listOf(
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        "yyyy-MM-dd'T'HH:mm:ss'Z'",
        "yyyy-MM-dd",
        "dd MMM yyyy",
        "dd MMMM yyyy",
        "d MMMM yyyy"
    )

    for (pattern in patterns) {
        try {
            val parser = SimpleDateFormat(pattern, locale)
            parser.isLenient = false
            val date = parser.parse(value)
            if (date != null) {
                return date.time
            }
        } catch (_: Exception) {
        }
    }

    return 0L
}