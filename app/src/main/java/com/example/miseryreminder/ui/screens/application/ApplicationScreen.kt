package com.example.miseryreminder.ui.screens.application

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.miseryreminder.data.database.ApplicationEntity
import com.example.miseryreminder.data.database.Status
import com.example.miseryreminder.ui.utils.AddApplicationDialog

@Composable
fun ApplicationScreen(
    modifier: Modifier = Modifier,
    applications: List<ApplicationEntity>,
    paddingValues: PaddingValues,
    isDarkMode: Boolean,
    onStatusUpdate: (ApplicationEntity, Status) -> Unit,
    onAddApplication: (String) -> Unit
) {
    var selectedApplication by remember { mutableStateOf<ApplicationEntity?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Application"
                )
            }
        }
    ) {
        LazyColumn(
            modifier = modifier.padding(paddingValues),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            items(
                count = applications.size,
                key = { applications[it].applicationId }
            ) { i ->
                val app = applications[i]
                ApplicationItem(
                    application = app,
                    onClick = { selectedApplication = app },
                    isDarkMode = isDarkMode,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
        }
    }
    if (showAddDialog) {
        AddApplicationDialog(
            onAddApplication = { companyName ->
                onAddApplication(companyName)
            },
            onDismiss = { showAddDialog = false }
        )
    }
    selectedApplication?.let { application ->
        ApplicationStatusBottomSheet(
            application = application,
            onStatusChanged = { newStatus ->
                onStatusUpdate(application, newStatus)
            },
            onDismiss = { selectedApplication = null }
        )
    }

}

@Preview(showSystemUi = true)
@Composable
fun ApplicationScreenPreview() {
    ApplicationScreen(
        applications = listOf<ApplicationEntity>(
            ApplicationEntity(
                1,
                "Instabug",
                System.currentTimeMillis(),
                Status.REJECTED
            ),
            ApplicationEntity(
                2,
                "Microsoft",
                System.currentTimeMillis(),
                Status.PENDING
            ),
            ApplicationEntity(
                3,
                "Bosta",
                System.currentTimeMillis(),
                Status.INTERVIEWED
            ),
            ApplicationEntity(
                4,
                "Military",
                System.currentTimeMillis(),
                Status.ACCEPTED
            )
        ),
        onStatusUpdate = { _, _ -> },
        paddingValues = PaddingValues(0.dp),
        isDarkMode = true,
        onAddApplication = { }
    )
}