package com.palak.medyora.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.LocationOff
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.palak.medyora.utils.AppException
import com.palak.medyora.utils.toUserMessage


// 1. Full screen error — blocks the entire screen, has retry button
// Use when: the entire screen's data failed to load

@Composable
fun FullScreenError(
    exception: AppException,
    onRetry: (() -> Unit)? = null
) {
    Column(

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(24.dp)
    ) {
        Icon(
            imageVector = when (exception) {
                is AppException.NoInternetException -> Icons.Default.WifiOff
                is AppException.LocationPermissionDeniedException -> Icons.Default.LocationOff
                else -> Icons.Default.ErrorOutline
            },
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = exception.toUserMessage(),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
        if (onRetry != null) {
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onRetry) {
                Text("Try Again")
            }
        }
    }
}


// 2. Inline error — small text under a field
// Use when: form validation, wrong password, empty field
@Composable
fun InlineError(
    exception: AppException,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(top = 4.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ErrorOutline,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = exception.toUserMessage(),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.error
        )
    }
}