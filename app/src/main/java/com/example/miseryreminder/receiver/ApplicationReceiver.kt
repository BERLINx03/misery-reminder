package com.example.miseryreminder.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.miseryreminder.MiseryApplication
import com.example.miseryreminder.alarm.ACTION_NO
import com.example.miseryreminder.alarm.ACTION_YES
import com.example.miseryreminder.ui.utils.NOTIFICATION2_ID
import com.example.miseryreminder.ui.utils.sendApplicationNotifications
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ApplicationReceiver: BroadcastReceiver() {
    override fun onReceive(ctx: Context, i: Intent) {
        val notificationManager = ctx.getSystemService(NotificationManager::class.java) as NotificationManager
        notificationManager.sendApplicationNotifications(
            context = ctx,
            title = "Did you apply for any jobs today?",
            message = "Did you apply for any jobs today?"
        )
        when (i.action) {
            ACTION_YES -> {
                val app = ctx.applicationContext as MiseryApplication
                CoroutineScope(Dispatchers.IO).launch {
                    app.preferences.saveApplications()
                }
                notificationManager.cancel(NOTIFICATION2_ID)
            }

            ACTION_NO -> {
                notificationManager.cancel(NOTIFICATION2_ID)
            }
        }
    }
}