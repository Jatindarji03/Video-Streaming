package com.example.videostreaming.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.videostreaming.ui.theme.LightGray

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    hint: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
    val isFocused = remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        Text(
            text = label, color = Color.White, style = MaterialTheme.typography.labelLarge
        )
        Box(modifier = Modifier
            .fillMaxWidth(0.8f)
            .background(Color.DarkGray, RoundedCornerShape(8.dp))
            .border(
                width = 2.dp,
                color = if (isFocused.value) Color.White else Color.DarkGray,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
            .focusRequester(focusRequester)
            .onFocusChanged {
                isFocused.value = it.isFocused
            }) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(end = 8.dp)
                )
                if (value.isEmpty() && !isFocused.value) {
                    Text(
                        text = hint,
                        style = MaterialTheme.typography.bodyLarge.copy(color = LightGray)
                    )
                }
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

    }

}