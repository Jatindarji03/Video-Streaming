package com.example.videostreaming.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.videostreaming.ui.theme.VideoStreamingTheme

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    val email = remember { mutableStateOf(TextFieldValue("")) }
    val password = remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(color = Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Welcome Back",
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.White // Assuming you want the text to be white
                )
                Spacer(modifier = Modifier.height(24.dp))
                CustomTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = "Email",
                    hint = "Email",
                    icon = Icons.Filled.Email
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = "Password",
                    hint = "Password",
                    icon = Icons.Filled.Lock
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { /* Handle login logic */ },
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text(text = "Login")
                }
            }
        }
    }
}

@Composable
fun CustomTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    label: String,
    hint: String,
    icon: ImageVector
) {
    val focusRequester = remember { FocusRequester() }
    val isFocused = remember { mutableStateOf(false) }

    Column {
        Text(text = label, color = Color.White, style = MaterialTheme.typography.labelLarge)
        Box(
            modifier = Modifier
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
                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(end = 8.dp)
                )
                if (value.text.isEmpty() && !isFocused.value) {
                    Text(
                        text = hint,
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray)
                    )
                }
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun LoginScreenPreview() {
    VideoStreamingTheme {
        LoginScreen()
    }
}
