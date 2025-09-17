package com.example.miseryreminder.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.miseryreminder.alarm.ACTION_NO
import com.example.miseryreminder.alarm.ACTION_YES
import com.example.miseryreminder.ui.utils.NOTIFICATION2_ID
import com.example.miseryreminder.ui.utils.sendAreYouHirdSon

const val HIRED = "hired"
class ApplicationReceiver: BroadcastReceiver() {
    override fun onReceive(ctx: Context, i: Intent) {
        val notificationManager = ctx.getSystemService(NotificationManager::class.java) as NotificationManager
        notificationManager.sendAreYouHirdSon(
            context = ctx,
            title = "Are you hired son?",
            message = ""
        )
        when (i.action) {
            ACTION_YES -> {
                notificationManager.cancel(NOTIFICATION2_ID)
            }

            ACTION_NO -> {
                notificationManager.cancel(NOTIFICATION2_ID)
            }
        }
    }
}