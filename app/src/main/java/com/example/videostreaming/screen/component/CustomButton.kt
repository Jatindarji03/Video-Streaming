package com.example.videostreaming.screen.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.videostreaming.ui.theme.Black
import com.example.videostreaming.ui.theme.Blue

@Composable
fun CustomButton(
    textToDisplay: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(0.8f),
        colors = ButtonDefaults.buttonColors(
            containerColor = Blue,
            contentColor = Black
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Text(
            text = textToDisplay,
            style = MaterialTheme.typography.labelLarge
        )

    }
}