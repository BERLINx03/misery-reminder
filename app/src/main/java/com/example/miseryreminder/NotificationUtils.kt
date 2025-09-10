package com.example.miseryreminder

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

const val CHANNEL_ID = "id alarm"
const val CHANNEL_NAME = "alarm"
const val NOTIFICATION_ID = 1
fun NotificationManager.sendAlarmNotification(context: Context){
    val pendingIntent = PendingIntent.getActivity(
        context,
        1,
        Intent(context, MainActivity::class.java),
        PendingIntent.FLAG_IMMUTABLE
    )
    val builder = NotificationCompat.Builder(
        context,
        CHANNEL_ID
    ).apply {
        setSmallIcon(R.drawable.ic_launcher_foreground)
        setContentTitle("Hustle ya 3el2")
        setContentText("Today is the youngest you will ever be don't waste it!")
        setAutoCancel(true)
        setContentIntent(pendingIntent)
    }

    notify(NOTIFICATION_ID, builder.build())
}