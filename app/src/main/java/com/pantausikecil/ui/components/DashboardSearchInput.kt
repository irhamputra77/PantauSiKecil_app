package com.pantausikecil.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun DashboardSearchInput(
    value: String,
    onValueChange: (String) -> Unit,
    isTablet: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (isTablet) figmaDp(70f) else figmaDp(42f))
            .shadow(
                elevation = if (isTablet) figmaDp(5f) else figmaDp(3f),
                shape = RoundedCornerShape(999.dp)
            )
            .border(
                width = 1.dp,
                color = Color(0xFF5F5F5F),
                shape = RoundedCornerShape(999.dp)
            )
            .background(Color.White, RoundedCornerShape(999.dp))
            .padding(horizontal = if (isTablet) figmaDp(33f) else figmaDp(20f)),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(if (isTablet) figmaDp(34f) else figmaDp(22f))
            )

            Spacer(modifier = Modifier.width(if (isTablet) figmaDp(18f) else figmaDp(10f)))

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = if (isTablet) figmaSp(24f) else figmaSp(15f)
                ),
                modifier = Modifier.weight(1f)
            )
        }
    }
}
