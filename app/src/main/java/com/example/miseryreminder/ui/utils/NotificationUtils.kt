package com.example.miseryreminder.ui.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.example.miseryreminder.MainActivity
import com.example.miseryreminder.R
import com.example.miseryreminder.alarm.ACTION_NO
import com.example.miseryreminder.alarm.ACTION_YES
import com.example.miseryreminder.alarm.AlarmReceiver
import com.example.miseryreminder.receiver.ApplicationReceiver
import com.example.miseryreminder.receiver.HIRED

const val CHANNEL1_ID = "alarm_channel"
const val CHANNEL1_NAME = "alarm_channel_name"

const val CHANNEL2_ID = "are_you_hired_channel"
const val CHANNEL2_NAME = "hired_channel"
const val NOTIFICATION_ID = 1
const val NOTIFICATION2_ID = 2
fun NotificationManager.sendAlarmNotification(context: Context, title: String, message: String) {

    val pendingIntent = PendingIntent.getActivity(
        context,
        1,
        Intent(context, MainActivity::class.java),
        PendingIntent.FLAG_IMMUTABLE
    )
    val yesIntent = Intent(context, AlarmReceiver::class.java).apply {
        action = ACTION_YES
    }
    val yesPendingIntent = PendingIntent.getBroadcast(
        context,
        2,
        yesIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val noIntent = Intent(context, AlarmReceiver::class.java).apply {
        action = ACTION_NO
    }
    val noPendingIntent = PendingIntent.getBroadcast(
        context,
        3,
        noIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val builder = NotificationCompat.Builder(
        context,
        CHANNEL1_ID
    ).apply {
        setSmallIcon(R.drawable.ic_launcher_background)
        setContentTitle(title)
        setContentText(message)
        setPriority(NotificationCompat.PRIORITY_HIGH)
        setAutoCancel(false)
        addAction(
            R.drawable.yes,
            "Mark Complete",
            yesPendingIntent
        )
        addAction(
            R.drawable.no,
            "Skip Today",
            noPendingIntent
        )
        setContentIntent(pendingIntent)
    }
    notify(NOTIFICATION_ID, builder.build())
}

fun NotificationManager.sendAreYouHirdSon(
    context: Context,
    title: String,
    message: String
) {
    val screenTimeImage = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.are_winning_son
    )
    val bigPicStyle = NotificationCompat.BigPictureStyle()
        .bigPicture(screenTimeImage)
        .bigLargeIcon(null as Bitmap?)
    val pendingIntent = PendingIntent.getActivity(
        context,
        1,
        Intent(context, MainActivity::class.java),
        PendingIntent.FLAG_IMMUTABLE
    )

    val yesIntent = Intent(context, MainActivity::class.java).apply {
        action = ACTION_YES
        putExtra(HIRED, true)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
    }
    val yesPendingIntent = PendingIntent.getActivity(
        context,
        9,
        yesIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val noIntent = Intent(context, ApplicationReceiver::class.java).apply {
        action = ACTION_NO
    }
    val noPendingIntent = PendingIntent.getBroadcast(
        context,
        3,
        noIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    val builder = NotificationCompat.Builder(
        context,
        CHANNEL2_ID
    ).apply {
        setSmallIcon(R.drawable.ic_launcher_background)
        setContentTitle(title)
        setContentText(message)
        setStyle(bigPicStyle)
        addAction(
            R.drawable.yes,
            "Yes!",
            yesPendingIntent
        )
        addAction(
            R.drawable.no,
            "No",
            noPendingIntent
        )
        setLargeIcon(screenTimeImage)
        setAutoCancel(true)
        setContentIntent(pendingIntent)
    }
    notify(NOTIFICATION2_ID, builder.build())
}