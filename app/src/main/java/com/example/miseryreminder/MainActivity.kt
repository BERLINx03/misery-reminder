package com.example.miseryreminder

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.miseryreminder.alarm.AlarmSchedular
import com.example.miseryreminder.data.database.ApplicationDatabase
import com.example.miseryreminder.data.preferences.PreferencesViewModel
import com.example.miseryreminder.data.preferences.PreferencesViewModelFactory
import com.example.miseryreminder.ui.NavigationDrawer
import com.example.miseryreminder.ui.screens.application.ApplicationViewModel
import com.example.miseryreminder.ui.screens.application.ApplicationViewModelFactory
import com.example.miseryreminder.ui.theme.MiseryReminderTheme

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (!isGranted) {
                Toast.makeText(
                    this,
                    "Notifications are disabled. Enable them in Settings.",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                }
                startActivity(intent)
            }

        }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        askNotificationPermission()
        val alarmSchedular = AlarmSchedular(this)
        val dao = ApplicationDatabase.getInstance(this).applicationDao
        val applicationViewModel = ViewModelProvider(
            this,
            ApplicationViewModelFactory(dao)
        )[ApplicationViewModel::class.java]
        val prefsViewModel = ViewModelProvider(
            this,
            PreferencesViewModelFactory((application as MiseryApplication).preferences)
        )[PreferencesViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            val startDate = prefsViewModel.startDate.collectAsState().value
            val today = System.currentTimeMillis()
            val daysElapsed = (today - startDate) / (1000 * 60 * 60 * 24)
            MiseryReminderTheme(prefsViewModel.isDarkMode.collectAsState().value) {
                val navController = rememberNavController()
                NavigationDrawer(
                    navController = navController,
                    applicationViewModel = applicationViewModel,
                    prefsViewModel = prefsViewModel,
                    alarmSchedular = alarmSchedular,
                    daysElapsed = daysElapsed
                )
            }
        }
    }
}