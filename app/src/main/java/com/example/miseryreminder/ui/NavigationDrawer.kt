package com.example.miseryreminder.ui

import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Article
import androidx.compose.material.icons.automirrored.outlined.Help
import androidx.compose.material.icons.automirrored.rounded.Assignment
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.miseryreminder.R
import com.example.miseryreminder.alarm.AlarmSchedular
import com.example.miseryreminder.data.database.ApplicationEntity
import com.example.miseryreminder.data.database.Status
import com.example.miseryreminder.data.preferences.PreferencesViewModel
import com.example.miseryreminder.openGivenProfile
import com.example.miseryreminder.ui.screens.Screens
import com.example.miseryreminder.ui.screens.application.ApplicationScreen
import com.example.miseryreminder.ui.screens.application.ApplicationViewModel
import com.example.miseryreminder.ui.screens.settings.SettingsScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    applicationViewModel: ApplicationViewModel,
    prefsViewModel: PreferencesViewModel,
    alarmSchedular: AlarmSchedular,
    daysElapsed: Long,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val isDarkMode = prefsViewModel.isDarkMode.collectAsState().value
    ModalNavigationDrawer(
        modifier = modifier,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Your misery reminder",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                    HorizontalDivider()

                    NavigationDrawerItem(
                        label = { Text("Home") },
                        selected = false,
                        icon = { Icon(Icons.Rounded.Home, contentDescription = null) },
                        onClick = {
                            navController.navigate(Screens.Home.route)
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Applications") },
                        icon = {
                            Icon(
                                Icons.AutoMirrored.Rounded.Assignment,
                                contentDescription = null
                            )
                        },
                        selected = false,
                        onClick = {
                            navController.navigate(Screens.Applications.route)
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Settings") },
                        selected = false,
                        icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                        onClick = {
                            navController.navigate(Screens.Settings.route)
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        "Support",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                    NavigationDrawerItem(
                        label = { Text("Help and feedback") },
                        selected = false,
                        icon = {
                            Icon(
                                Icons.AutoMirrored.Outlined.Help,
                                contentDescription = null
                            )
                        },
                        onClick = {
                            openGivenProfile(
                                context,
                                "https://discord.gg/XZD9xjmbCU",
                                "com.discord"
                            )
                        },
                    )
                    NavigationDrawerItem(
                        label = { Text("About") },
                        selected = false,
                        icon = {
                            Icon(
                                Icons.AutoMirrored.Outlined.Article,
                                contentDescription = null
                            )
                        },
                        onClick = {
                            openGivenProfile(
                                context,
                                "https://x.com/BERLINx03",
                                "com.twitter"
                            )
                        },
                    )
                    Spacer(Modifier.height(12.dp))
                }
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .height(70.dp)
                        .background(Color.Transparent),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
                ) {
                    IconButton(onClick = {
                        scope.launch {
                            if (drawerState.isClosed) {
                                drawerState.open()
                            } else {
                                drawerState.close()
                            }
                        }
                    }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            },

        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screens.Home.route,
            ) {
                composable(Screens.Home.route) {
                    val hustledDays = prefsViewModel.hustleDays.collectAsState().value
                    val applications = prefsViewModel.applications.collectAsState().value
                    MainScreen(alarmSchedular, daysElapsed, hustledDays, applications, isDarkMode)
                }
                composable(Screens.Applications.route) {
                    val applications = applicationViewModel.applications.collectAsState()
                    ApplicationScreen(
                        applications = applications.value,
                        isDarkMode = isDarkMode,
                        paddingValues = innerPadding,
                        onStatusUpdate = { app, status ->
                            var sound: MediaPlayer? = null
                            if (status == Status.REJECTED) {
                                sound = MediaPlayer.create(context, R.raw.womp_womp)
                            } else if (status == Status.ACCEPTED) {
                                sound = MediaPlayer.create(context, R.raw.tobi)
                            }
                            sound?.start()
                            sound?.setOnCompletionListener {
                                it.release()
                            }
                            applicationViewModel.upsertApplication(app.copy(applicationStatus = status))
                        },
                        onAddApplication = { company ->
                            applicationViewModel.upsertApplication(
                                ApplicationEntity(
                                    companyName = company,
                                    applyingDate = System.currentTimeMillis(),
                                    applicationStatus = Status.PENDING
                                )
                            )
                        }
                    )
                }
                composable(Screens.Settings.route) {
                    SettingsScreen(prefsViewModel, innerPadding)
                }
            }
        }
    }
}