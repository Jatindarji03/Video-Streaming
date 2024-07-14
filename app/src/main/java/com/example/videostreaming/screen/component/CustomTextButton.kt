package com.example.videostreaming.screen.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.videostreaming.ui.theme.Blue
import com.example.videostreaming.ui.theme.LightGray

@Composable
fun CustomTextButton(
    firstText: String,
    secondText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(0.8f)
    ) {
        val textToDisplay = buildAnnotatedString {
            withStyle(style = SpanStyle(color = LightGray)) {
                append(firstText)
            }
            withStyle(style = SpanStyle(color = Blue)) {
                append(secondText)
            }
        }
        Text(
            text = textToDisplay,
            style = MaterialTheme.typography.bodyLarge
        )
    }

}