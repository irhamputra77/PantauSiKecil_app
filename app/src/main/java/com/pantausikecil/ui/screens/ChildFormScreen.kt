package com.pantausikecil.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pantausikecil.model.ChildFormInput
import com.pantausikecil.model.Gender
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val FIGMA_TO_COMPOSE_SCALE = 1.5f

private fun figmaDp(px: Float): Dp {
    return (px / FIGMA_TO_COMPOSE_SCALE).dp
}

private fun figmaSp(px: Float): TextUnit {
    return (px / FIGMA_TO_COMPOSE_SCALE).sp
}

@Composable
fun ChildFormScreen(
    onBack: () -> Unit,
    onSave: (ChildFormInput) -> Unit
) {
    var parentName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    var rt by remember { mutableStateOf("") }
    var rw by remember { mutableStateOf("") }
    var kelurahan by remember { mutableStateOf("") }
    var kecamatan by remember { mutableStateOf("") }
    var kabupatenKota by remember { mutableStateOf("") }

    var childName by remember { mutableStateOf("") }
    var nik by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var birthPlace by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf<Gender?>(null) }

    var showBirthDatePicker by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var success by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    fun submitForm() {
        val cleanNik = nik.trim()
        val cleanPhone = phone.filter { it.isDigit() }
        val dateRegex = Regex("\\d{4}-\\d{2}-\\d{2}")

        errorMessage = when {
            parentName.trim().isBlank() -> "Nama orang tua wajib diisi."
            cleanPhone.isBlank() -> "Nomor telepon wajib diisi."
            address.trim().isBlank() -> "Alamat wajib diisi."
            kelurahan.trim().isBlank() -> "Kelurahan wajib diisi."
            childName.trim().isBlank() -> "Nama anak wajib diisi."
            cleanNik.isBlank() -> "NIK anak wajib diisi."
            !Regex("\\d{16}").matches(cleanNik) -> "NIK harus 16 digit angka."
            birthDate.trim().isBlank() -> "Tanggal lahir wajib diisi."
            !dateRegex.matches(birthDate.trim()) -> "Format tanggal lahir harus YYYY-MM-DD."
            birthPlace.trim().isBlank() -> "Tempat lahir wajib diisi."
            gender == null -> "Jenis kelamin wajib dipilih."
            else -> null
        }

        if (errorMessage != null) return

        isLoading = true

        onSave(
            ChildFormInput(
                nama = childName.trim(),
                nik = cleanNik,
                jenisKelamin = gender!!,
                tempatLahir = birthPlace.trim(),
                tanggalLahir = birthDate.trim(),
                namaOrangTua = parentName.trim(),
                nomorOrangTua = cleanPhone,
                alamatAnak = address.trim(),
                rtAnak = rt.trim(),
                rwAnak = rw.trim(),
                kelurahan = kelurahan.trim(),
                kecamatan = kecamatan.trim(),
                kabupatenKota = kabupatenKota.trim()
            )
        )

        isLoading = false
        success = true
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .imePadding()
    ) {
        val isTablet = maxWidth >= 700.dp

        val headerHeight = if (isTablet) figmaDp(225f) else figmaDp(150f)
        val formWidth = if (isTablet) maxWidth * 0.69f else maxWidth * 0.82f
        val formMaxWidth = if (isTablet) figmaDp(860f) else figmaDp(760f)

        val backStart = if (isTablet) figmaDp(42f) else figmaDp(30f)
        val backTop = if (isTablet) figmaDp(48f) else figmaDp(34f)
        val backIconSize = if (isTablet) figmaDp(32f) else figmaDp(27f)
        val backTextSize = if (isTablet) figmaSp(30f) else figmaSp(23f)

        val titleSize = if (isTablet) figmaSp(58f) else figmaSp(45f)

        val labelSize = if (isTablet) figmaSp(26f) else figmaSp(21f)
        val placeholderSize = if (isTablet) figmaSp(23f) else figmaSp(18f)
        val inputTextSize = if (isTablet) figmaSp(23f) else figmaSp(18f)

        val fieldHeight = if (isTablet) figmaDp(64f) else figmaDp(54f)
        val fieldRadius = if (isTablet) figmaDp(12f) else figmaDp(10f)
        val fieldIconSize = if (isTablet) figmaDp(27f) else figmaDp(23f)

        val sectionGap = if (isTablet) figmaDp(30f) else figmaDp(22f)
        val labelFieldGap = if (isTablet) figmaDp(10f) else figmaDp(8f)
        val rowGap = if (isTablet) figmaDp(27f) else figmaDp(14f)

        val rtRwWidth = if (isTablet) figmaDp(60f) else figmaDp(52f)

        val radioSize = if (isTablet) figmaDp(25f) else figmaDp(22f)
        val radioTextSize = if (isTablet) figmaSp(24f) else figmaSp(20f)

        val buttonHeight = if (isTablet) figmaDp(68f) else figmaDp(58f)
        val buttonRadius = if (isTablet) figmaDp(12f) else figmaDp(10f)
        val buttonTextSize = if (isTablet) figmaSp(28f) else figmaSp(23f)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(headerHeight)
            ) {
                Row(
                    modifier = Modifier
                        .padding(
                            start = backStart,
                            top = backTop
                        )
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = onBack
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Kembali",
                        tint = Color.Black,
                        modifier = Modifier.size(backIconSize)
                    )

                    Spacer(modifier = Modifier.width(figmaDp(10f)))

                    Text(
                        text = "Kembali",
                        color = Color.Black,
                        fontSize = backTextSize,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Text(
                    text = "Data Anak",
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth(),
                    color = Color.Black,
                    fontSize = titleSize,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(
                modifier = Modifier.height(
                    if (isTablet) figmaDp(48f) else figmaDp(36f)
                )
            )

            Column(
                modifier = Modifier
                    .widthIn(max = formMaxWidth)
                    .width(formWidth),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FigmaLabeledTextField(
                    value = parentName,
                    onValueChange = { parentName = it },
                    label = "Nama Orang Tua",
                    placeholder = "Contoh : Rini Aisyah",
                    icon = Icons.Filled.Person,
                    labelSize = labelSize,
                    placeholderSize = placeholderSize,
                    inputTextSize = inputTextSize,
                    fieldHeight = fieldHeight,
                    fieldRadius = fieldRadius,
                    iconSize = fieldIconSize,
                    labelFieldGap = labelFieldGap,
                    keyboardType = KeyboardType.Text
                )

                Spacer(modifier = Modifier.height(sectionGap))

                FigmaLabeledTextField(
                    value = phone,
                    onValueChange = { value ->
                        phone = value.filter { it.isDigit() }.take(15)
                    },
                    label = "Nomor Telepon",
                    placeholder = "08XX-XXXX-XXXX",
                    icon = Icons.Filled.Phone,
                    labelSize = labelSize,
                    placeholderSize = placeholderSize,
                    inputTextSize = inputTextSize,
                    fieldHeight = fieldHeight,
                    fieldRadius = fieldRadius,
                    iconSize = fieldIconSize,
                    labelFieldGap = labelFieldGap,
                    keyboardType = KeyboardType.Phone
                )

                Spacer(modifier = Modifier.height(sectionGap))

                FigmaLabeledTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = "Alamat",
                    placeholder = "Contoh : Cedek e smancol",
                    icon = Icons.Filled.Home,
                    labelSize = labelSize,
                    placeholderSize = placeholderSize,
                    inputTextSize = inputTextSize,
                    fieldHeight = fieldHeight,
                    fieldRadius = fieldRadius,
                    iconSize = fieldIconSize,
                    labelFieldGap = labelFieldGap,
                    keyboardType = KeyboardType.Text
                )

                Spacer(modifier = Modifier.height(sectionGap))

                FigmaLabeledTextField(
                    value = kelurahan,
                    onValueChange = { kelurahan = it },
                    label = "Kelurahan",
                    placeholder = "Contoh : Sukabirus",
                    icon = Icons.Filled.LocationOn,
                    labelSize = labelSize,
                    placeholderSize = placeholderSize,
                    inputTextSize = inputTextSize,
                    fieldHeight = fieldHeight,
                    fieldRadius = fieldRadius,
                    iconSize = fieldIconSize,
                    labelFieldGap = labelFieldGap,
                    keyboardType = KeyboardType.Text
                )

                Spacer(modifier = Modifier.height(sectionGap))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(rowGap),
                    verticalAlignment = Alignment.Bottom
                ) {
                    FigmaLabeledTextField(
                        value = rt,
                        onValueChange = { value ->
                            rt = value.filter { it.isDigit() }.take(3)
                        },
                        label = "RT",
                        placeholder = "",
                        icon = null,
                        modifier = Modifier.width(rtRwWidth),
                        labelSize = labelSize,
                        placeholderSize = placeholderSize,
                        inputTextSize = inputTextSize,
                        fieldHeight = fieldHeight,
                        fieldRadius = fieldRadius,
                        iconSize = fieldIconSize,
                        labelFieldGap = labelFieldGap,
                        keyboardType = KeyboardType.Number
                    )

                    FigmaLabeledTextField(
                        value = rw,
                        onValueChange = { value ->
                            rw = value.filter { it.isDigit() }.take(3)
                        },
                        label = "RW",
                        placeholder = "",
                        icon = null,
                        modifier = Modifier.width(rtRwWidth),
                        labelSize = labelSize,
                        placeholderSize = placeholderSize,
                        inputTextSize = inputTextSize,
                        fieldHeight = fieldHeight,
                        fieldRadius = fieldRadius,
                        iconSize = fieldIconSize,
                        labelFieldGap = labelFieldGap,
                        keyboardType = KeyboardType.Number
                    )

                    FigmaLabeledTextField(
                        value = kecamatan,
                        onValueChange = { kecamatan = it },
                        label = "Kecamatan",
                        placeholder = "",
                        icon = null,
                        modifier = Modifier.weight(1f),
                        labelSize = labelSize,
                        placeholderSize = placeholderSize,
                        inputTextSize = inputTextSize,
                        fieldHeight = fieldHeight,
                        fieldRadius = fieldRadius,
                        iconSize = fieldIconSize,
                        labelFieldGap = labelFieldGap,
                        keyboardType = KeyboardType.Text
                    )

                    FigmaLabeledTextField(
                        value = kabupatenKota,
                        onValueChange = { kabupatenKota = it },
                        label = "Kabupaten",
                        placeholder = "",
                        icon = null,
                        modifier = Modifier.weight(1.16f),
                        labelSize = labelSize,
                        placeholderSize = placeholderSize,
                        inputTextSize = inputTextSize,
                        fieldHeight = fieldHeight,
                        fieldRadius = fieldRadius,
                        iconSize = fieldIconSize,
                        labelFieldGap = labelFieldGap,
                        keyboardType = KeyboardType.Text
                    )
                }

                Spacer(
                    modifier = Modifier.height(
                        if (isTablet) figmaDp(44f) else figmaDp(32f)
                    )
                )

                FigmaLabeledTextField(
                    value = childName,
                    onValueChange = { childName = it },
                    label = "Nama Anak",
                    placeholder = "Contoh : Al Hoshino",
                    icon = Icons.Filled.Person,
                    labelSize = labelSize,
                    placeholderSize = placeholderSize,
                    inputTextSize = inputTextSize,
                    fieldHeight = fieldHeight,
                    fieldRadius = fieldRadius,
                    iconSize = fieldIconSize,
                    labelFieldGap = labelFieldGap,
                    keyboardType = KeyboardType.Text
                )

                Spacer(modifier = Modifier.height(sectionGap))

                FigmaLabeledTextField(
                    value = nik,
                    onValueChange = { value ->
                        nik = value.filter { it.isDigit() }.take(16)
                    },
                    label = "NIK Anak",
                    placeholder = "Contoh : 320XXXXXXXXXXXXX",
                    icon = Icons.Filled.Badge,
                    labelSize = labelSize,
                    placeholderSize = placeholderSize,
                    inputTextSize = inputTextSize,
                    fieldHeight = fieldHeight,
                    fieldRadius = fieldRadius,
                    iconSize = fieldIconSize,
                    labelFieldGap = labelFieldGap,
                    keyboardType = KeyboardType.Number
                )

                Spacer(modifier = Modifier.height(sectionGap))

                FigmaLabeledDateField(
                    value = birthDate,
                    onValueChange = { birthDate = it },
                    onCalendarClick = {
                        showBirthDatePicker = true
                    },
                    label = "Tanggal Lahir",
                    placeholder = "",
                    labelSize = labelSize,
                    placeholderSize = placeholderSize,
                    inputTextSize = inputTextSize,
                    fieldHeight = fieldHeight,
                    fieldRadius = fieldRadius,
                    iconSize = fieldIconSize,
                    labelFieldGap = labelFieldGap
                )

                Spacer(modifier = Modifier.height(sectionGap))

                FigmaLabeledTextField(
                    value = birthPlace,
                    onValueChange = { birthPlace = it },
                    label = "Tempat Lahir",
                    placeholder = "Contoh : Yogyakarta",
                    icon = Icons.Filled.DateRange,
                    labelSize = labelSize,
                    placeholderSize = placeholderSize,
                    inputTextSize = inputTextSize,
                    fieldHeight = fieldHeight,
                    fieldRadius = fieldRadius,
                    iconSize = fieldIconSize,
                    labelFieldGap = labelFieldGap,
                    keyboardType = KeyboardType.Text
                )

                Spacer(modifier = Modifier.height(sectionGap))

                Text(
                    text = "Jenis Kelamin",
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black,
                    fontSize = labelSize,
                    fontWeight = FontWeight.Normal
                )

                Spacer(
                    modifier = Modifier.height(
                        if (isTablet) figmaDp(34f) else figmaDp(24f)
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = if (isTablet) figmaDp(58f) else figmaDp(20f)
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FigmaRadioOption(
                        text = "Laki - Laki",
                        selected = gender == Gender.Male,
                        radioSize = radioSize,
                        textSize = radioTextSize,
                        onClick = {
                            gender = Gender.Male
                        }
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    FigmaRadioOption(
                        text = "Perempuan",
                        selected = gender == Gender.Female,
                        radioSize = radioSize,
                        textSize = radioTextSize,
                        onClick = {
                            gender = Gender.Female
                        }
                    )
                }

                if (!errorMessage.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(figmaDp(24f)))

                    Text(
                        text = errorMessage.orEmpty(),
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(0xFFC62828),
                        fontSize = if (isTablet) figmaSp(21f) else figmaSp(17f),
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(
                    modifier = Modifier.height(
                        if (isTablet) figmaDp(110f) else figmaDp(78f)
                    )
                )

                FigmaGradientButton(
                    text = "Lanjutkan",
                    height = buttonHeight,
                    radius = buttonRadius,
                    textSize = buttonTextSize,
                    isLoading = isLoading,
                    onClick = ::submitForm
                )

                Spacer(
                    modifier = Modifier.height(
                        if (isTablet) figmaDp(110f) else figmaDp(80f)
                    )
                )
            }
        }
    }

    if (success) {
        SuccessDialog(
            message = "Data Berhasil Tersimpan",
            onBackToMenu = onBack
        )
    }

    if (showBirthDatePicker) {
        BirthDatePickerDialog(
            onDismiss = {
                showBirthDatePicker = false
            },
            onDateSelected = { selectedDate ->
                birthDate = selectedDate
                showBirthDatePicker = false
            }
        )
    }
}

@Composable
private fun FigmaLabeledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    icon: ImageVector?,
    labelSize: TextUnit,
    placeholderSize: TextUnit,
    inputTextSize: TextUnit,
    fieldHeight: Dp,
    fieldRadius: Dp,
    iconSize: Dp,
    labelFieldGap: Dp,
    keyboardType: KeyboardType,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            color = Color.Black,
            fontSize = labelSize,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(labelFieldGap))

        FigmaOutlinedInput(
            value = value,
            onValueChange = onValueChange,
            placeholder = placeholder,
            icon = icon,
            placeholderSize = placeholderSize,
            inputTextSize = inputTextSize,
            height = fieldHeight,
            radius = fieldRadius,
            iconSize = iconSize,
            keyboardType = keyboardType
        )
    }
}

@Composable
private fun FigmaLabeledDateField(
    value: String,
    onValueChange: (String) -> Unit,
    onCalendarClick: () -> Unit,
    label: String,
    placeholder: String,
    labelSize: TextUnit,
    placeholderSize: TextUnit,
    inputTextSize: TextUnit,
    fieldHeight: Dp,
    fieldRadius: Dp,
    iconSize: Dp,
    labelFieldGap: Dp,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = label,
            color = Color.Black,
            fontSize = labelSize,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(labelFieldGap))

        FigmaDateInput(
            value = value,
            onValueChange = onValueChange,
            onCalendarClick = onCalendarClick,
            placeholder = placeholder,
            placeholderSize = placeholderSize,
            inputTextSize = inputTextSize,
            height = fieldHeight,
            radius = fieldRadius,
            iconSize = iconSize
        )
    }
}

@Composable
private fun FigmaOutlinedInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector?,
    placeholderSize: TextUnit,
    inputTextSize: TextUnit,
    height: Dp,
    radius: Dp,
    iconSize: Dp,
    keyboardType: KeyboardType
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .border(
                width = 1.dp,
                color = Color(0xFF5F5F5F),
                shape = RoundedCornerShape(radius)
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(radius)
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = figmaDp(18f)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF8A8A8A),
                    modifier = Modifier.size(iconSize)
                )

                Spacer(modifier = Modifier.width(figmaDp(15f)))
            }

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType
                ),
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = inputTextSize,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.weight(1f),
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isEmpty() && placeholder.isNotBlank()) {
                            Text(
                                text = placeholder,
                                color = Color(0xFF909090),
                                fontSize = placeholderSize,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Normal
                            )
                        }

                        innerTextField()
                    }
                }
            )
        }
    }
}

@Composable
private fun FigmaDateInput(
    value: String,
    onValueChange: (String) -> Unit,
    onCalendarClick: () -> Unit,
    placeholder: String,
    placeholderSize: TextUnit,
    inputTextSize: TextUnit,
    height: Dp,
    radius: Dp,
    iconSize: Dp
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .border(
                width = 1.dp,
                color = Color(0xFF5F5F5F),
                shape = RoundedCornerShape(radius)
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(radius)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onCalendarClick
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = figmaDp(18f)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "Pilih tanggal",
                tint = Color(0xFF8A8A8A),
                modifier = Modifier.size(iconSize)
            )

            Spacer(modifier = Modifier.width(figmaDp(15f)))

            BasicTextField(
                value = value,
                onValueChange = { input ->
                    val filtered = input
                        .filter { it.isDigit() || it == '-' }
                        .take(10)

                    onValueChange(filtered)
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = inputTextSize,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.weight(1f),
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isEmpty() && placeholder.isNotBlank()) {
                            Text(
                                text = placeholder,
                                color = Color(0xFF909090),
                                fontSize = placeholderSize,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Normal
                            )
                        }

                        innerTextField()
                    }
                }
            )
        }
    }
}

@Composable
private fun FigmaRadioOption(
    text: String,
    selected: Boolean,
    radioSize: Dp,
    textSize: TextUnit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(radioSize)
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = CircleShape
                )
                .padding(radioSize * 0.22f),
            contentAlignment = Alignment.Center
        ) {
            if (selected) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(Color.Black)
                )
            }
        }

        Spacer(modifier = Modifier.width(figmaDp(20f)))

        Text(
            text = text,
            color = Color.Black,
            fontSize = textSize,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
private fun FigmaGradientButton(
    text: String,
    height: Dp,
    radius: Dp,
    textSize: TextUnit,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF27C840),
                        Color(0xFF34A853)
                    )
                ),
                shape = RoundedCornerShape(radius)
            )
            .clickable(
                enabled = !isLoading,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(figmaDp(32f)),
                strokeWidth = 2.5.dp,
                color = Color.White
            )
        } else {
            Text(
                text = text,
                color = Color.White,
                fontSize = textSize,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BirthDatePickerDialog(
    onDismiss: () -> Unit,
    onDateSelected: (String) -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    val selectedMillis = datePickerState.selectedDateMillis

                    if (selectedMillis != null) {
                        val formatter = SimpleDateFormat(
                            "yyyy-MM-dd",
                            Locale.getDefault()
                        )

                        val formattedDate = formatter.format(Date(selectedMillis))
                        onDateSelected(formattedDate)
                    } else {
                        onDismiss()
                    }
                }
            ) {
                Text(text = "Pilih")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(text = "Batal")
            }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}