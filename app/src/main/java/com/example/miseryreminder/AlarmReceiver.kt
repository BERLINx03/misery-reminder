package com.example.miseryreminder

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(ctx: Context, i: Intent?) {
        Log.d("AlarmScheduler", "onReceive triggered!")
        val notificationManager  = ctx.getSystemService(NotificationManager::class.java) as NotificationManager
        notificationManager.sendAlarmNotification(ctx)
    }
}