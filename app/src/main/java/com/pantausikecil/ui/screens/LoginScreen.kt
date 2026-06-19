package com.pantausikecil.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pantausikecil.ui.theme.PrimaryGreen

@Composable
fun LoginScreen(
    isLoading: Boolean = false,
    errorMessage: String? = null,
    onLogin: (email: String, password: String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF43B96A),
                        Color(0xFF57C77B),
                        Color(0xFFDDF6E4)
                    )
                )
            )
            .imePadding()
    ) {
        val isPhone = maxWidth < 430.dp
        val isTablet = maxWidth >= 700.dp

        val titleFontSize = when {
            isTablet -> 86.sp
            else -> 43.sp
        }

        val titleTopPadding = when {
            isTablet -> 180.dp
            else -> 90.dp
        }

        val titleToCardGap = when {
            isTablet -> 140.dp
            else -> 60.dp
        }

        val cardWidthFraction = when {
            isTablet -> 0.65f
            else -> 0.71f
        }

        val cardMaxWidth = if (isTablet) 620.dp else 285.dp
        val cardCornerRadius = if (isTablet) 28.dp else 11.dp
        val cardHorizontalPadding = if (isTablet) 62.dp else 24.dp
        val cardVerticalPadding = if (isTablet) 54.dp else 34.dp

        val loginTitleSize = if (isTablet) 40.sp else 21.sp
        val labelSize = if (isTablet) 20.sp else 8.sp
        val fieldHeight = if (isTablet) 56.dp else 23.dp
        val fieldRadius = if (isTablet) 10.dp else 5.dp
        val iconSize = if (isTablet) 28.dp else 12.dp
        val placeholderSize = if (isTablet) 18.sp else 8.sp
        val inputTextSize = if (isTablet) 18.sp else 8.sp

        val emailPasswordGap = if (isTablet) 42.dp else 22.dp
        val loginToEmailGap = if (isTablet) 48.dp else 28.dp
        val passwordToButtonGap = if (isTablet) 72.dp else 36.dp

        val buttonHeight = if (isTablet) 64.dp else 26.dp
        val buttonRadius = if (isTablet) 10.dp else 7.dp
        val buttonTextSize = if (isTablet) 24.sp else 11.sp

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = titleTopPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "PantauSiKecil",
                color = Color.White,
                fontSize = titleFontSize,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(titleToCardGap))

            Card(
                modifier = Modifier
                    .fillMaxWidth(cardWidthFraction)
                    .widthIn(max = cardMaxWidth),
                shape = RoundedCornerShape(cardCornerRadius),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 22.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = cardHorizontalPadding,
                            vertical = cardVerticalPadding
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Login",
                        color = Color.Black,
                        fontSize = loginTitleSize,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(loginToEmailGap))

                    LoginFieldLabel(
                        text = "Email",
                        fontSize = labelSize
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    FigmaLoginTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "Masukkan Email...",
                        enabled = !isLoading,
                        icon = Icons.Default.Mail,
                        height = fieldHeight,
                        radius = fieldRadius,
                        iconSize = iconSize,
                        placeholderFontSize = placeholderSize,
                        inputFontSize = inputTextSize,
                        keyboardType = KeyboardType.Email
                    )

                    Spacer(modifier = Modifier.height(emailPasswordGap))

                    LoginFieldLabel(
                        text = "Password",
                        fontSize = labelSize
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    FigmaLoginTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = "Masukkan Password....",
                        enabled = !isLoading,
                        icon = Icons.Default.Lock,
                        height = fieldHeight,
                        radius = fieldRadius,
                        iconSize = iconSize,
                        placeholderFontSize = placeholderSize,
                        inputFontSize = inputTextSize,
                        keyboardType = KeyboardType.Password,
                        isPassword = true
                    )

                    if (!errorMessage.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = errorMessage,
                            color = Color(0xFFC62828),
                            fontSize = if (isTablet) 14.sp else 9.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(passwordToButtonGap))

                    FigmaLoginButton(
                        text = "Masuk",
                        enabled = !isLoading,
                        isLoading = isLoading,
                        height = buttonHeight,
                        radius = buttonRadius,
                        fontSize = buttonTextSize,
                        onClick = {
                            onLogin(email.trim(), password)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun LoginFieldLabel(
    text: String,
    fontSize: TextUnit
) {
    Text(
        text = text,
        color = Color.Black,
        fontSize = fontSize,
        fontWeight = FontWeight.Normal,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun FigmaLoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    enabled: Boolean,
    icon: ImageVector,
    height: Dp,
    radius: Dp,
    iconSize: Dp,
    placeholderFontSize: TextUnit,
    inputFontSize: TextUnit,
    keyboardType: KeyboardType,
    isPassword: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(
                color = Color(0xFFD9D9D9),
                shape = RoundedCornerShape(radius)
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = if (height <= 30.dp) 8.dp else 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF777777),
                modifier = Modifier.size(iconSize)
            )

            Spacer(modifier = Modifier.size(if (height <= 30.dp) 8.dp else 16.dp))

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                enabled = enabled,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType
                ),
                visualTransformation = if (isPassword) {
                    PasswordVisualTransformation()
                } else {
                    VisualTransformation.None
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = inputFontSize,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier
                    .weight(1f),
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                color = Color(0xFF777777),
                                fontSize = placeholderFontSize,
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
private fun FigmaLoginButton(
    text: String,
    enabled: Boolean,
    isLoading: Boolean,
    height: Dp,
    radius: Dp,
    fontSize: TextUnit,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    val buttonGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF27C840),
            Color(0xFF506E58)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth(0.82f)
            .height(height)
            .background(
                brush = buttonGradient,
                shape = RoundedCornerShape(radius)
            )
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(if (height <= 30.dp) 14.dp else 24.dp),
                strokeWidth = 2.dp,
                color = Color.White
            )
        } else {
            Text(
                text = text,
                color = Color.White,
                fontSize = fontSize,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
        }
    }
}