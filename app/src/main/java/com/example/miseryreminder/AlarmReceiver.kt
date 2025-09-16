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
        if (i?.action == ACTION_DAILY_ALARM){
            val repeat = i.getBooleanExtra(DAILY_REPEAT, false)
            if(repeat){
                val alarm = AlarmSchedular(ctx)
                val hours = i.getIntExtra(HOURS, 0)
                val mins = i.getIntExtra(MINUTES, 0)
                alarm.setAlarmDaily(
                    hours = hours,
                    minutes = mins,
                    repeat = true,
                    onDone = { done, calendar ->
                        Log.d("AlarmScheduler", "Rescheduled: $done")
                    }
                )
            }
        }
    }
}