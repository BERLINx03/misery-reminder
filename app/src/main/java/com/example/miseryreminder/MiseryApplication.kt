package com.example.miseryreminder

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.core.net.toUri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.miseryreminder.data.preferences.ApplicationPreferences
import com.example.miseryreminder.data.preferences.PreferenceKeys
import com.example.miseryreminder.ui.utils.CHANNEL1_ID
import com.example.miseryreminder.ui.utils.CHANNEL1_NAME
import com.example.miseryreminder.ui.utils.CHANNEL2_ID
import com.example.miseryreminder.ui.utils.CHANNEL2_NAME

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = PreferenceKeys.PREFERENCE
)
class MiseryApplication: Application() {
    lateinit var preferences: ApplicationPreferences

    override fun onCreate() {
        super.onCreate()
        createChannel(CHANNEL1_ID, CHANNEL1_NAME,"Alarm notifications","android.resource://$packageName/${R.raw.ring}".toUri())
        createChannel(CHANNEL2_ID, CHANNEL2_NAME,"Apply notifications","android.resource://$packageName/${R.raw.screenshot_sound}".toUri())
        preferences = ApplicationPreferences(dataStore)
    }
    fun createChannel(channelId: String, channelName: String, description: String, sound: Uri? = null) {
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            this.description = description
            setSound(
                sound,
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
            enableVibration(true)
        }

        val notificationManager =
            getSystemService(NotificationManager::class.java) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}