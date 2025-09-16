package com.example.miseryreminder.alarm

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.miseryreminder.MiseryApplication
import com.example.miseryreminder.ui.utils.NOTIFICATION_ID
import com.example.miseryreminder.ui.utils.sendAlarmNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


const val ACTION_YES = "ACTION_YES"
const val ACTION_NO = "ACTION_NO"
class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(ctx: Context, i: Intent?) {
        Log.d("AlarmScheduler", "onReceive triggered!")
        when (i?.action) {
            ACTION_YES -> {
                val app = ctx.applicationContext as MiseryApplication
                CoroutineScope(Dispatchers.IO).launch {
                    app.preferences.saveHustleDays()
                }
                val notificationManager = ctx.getSystemService(NotificationManager::class.java)
                notificationManager.cancel(NOTIFICATION_ID)
            }

            ACTION_NO -> {
                val notificationManager = ctx.getSystemService(NotificationManager::class.java)
                notificationManager.cancel(NOTIFICATION_ID)
            }

            ACTION_DAILY_ALARM -> {
                val notificationManager = ctx.getSystemService(NotificationManager::class.java) as NotificationManager
                notificationManager.sendAlarmNotification(
                    context = ctx,
                    title = "Daily Check",
                    message = "Did you complete your hustle today?"
                )

                val repeat = i.getBooleanExtra(DAILY_REPEAT, false)
                if(repeat) {
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
}