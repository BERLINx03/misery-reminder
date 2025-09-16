package com.example.miseryreminder.ui.screens.application

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.miseryreminder.data.database.ApplicationEntity
import com.example.miseryreminder.data.database.Status
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ApplicationItem(
    application: ApplicationEntity,
    isDarkMode: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                role = Role.Button,
                indication = null,
                interactionSource = null
            ) { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = application.companyName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Applied: ${formatDate(application.applyingDate)}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusChip(status = application.applicationStatus, isDarkMode = isDarkMode)
            }
        }
    }
}

@Composable
fun StatusChip(
    status: Status,
    isDarkMode: Boolean,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, textColor, statusText) = when (status) {
        Status.PENDING -> Triple(
            if (isDarkMode) Color(0xFF3E3A1A) else Color(0xFFFFF3CD),
            if (isDarkMode) Color(0xFFFDD835) else Color(0xFF856404),
            "Pending"
        )
        Status.ACCEPTED -> Triple(
            if (isDarkMode) Color(0xFF1B3A1F) else Color(0xFFD4EDDA),
            if (isDarkMode) Color(0xFF4CAF50) else Color(0xFF155724),
            "Accepted"
        )
        Status.REJECTED -> Triple(
            if (isDarkMode) Color(0xFF3A1B1F) else Color(0xFFF8D7DA),
            if (isDarkMode) Color(0xFFEF5350) else Color(0xFF721C24),
            "Rejected"
        )
        Status.INTERVIEWED -> Triple(
            if (isDarkMode) Color(0xFF1B2E3A) else Color(0xFFD1ECF1),
            if (isDarkMode) Color(0xFF29B6F6) else Color(0xFF0C5460),
            "Interviewed"
        )
    }

    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = statusText,
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            fontWeight = FontWeight.Medium
        )
    }
}
private fun formatDate(timestamp: Long): String {
    val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return formatter.format(Date(timestamp))
}

@Preview
@Composable
fun ApplicationItemPreview(){
    ApplicationItem(
        ApplicationEntity(
            1,
            "Instabug",
            System.currentTimeMillis(),
            Status.REJECTED
        ),
        true
    )
}