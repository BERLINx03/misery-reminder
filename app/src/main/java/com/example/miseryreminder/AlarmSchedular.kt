package com.example.miseryreminder

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import java.util.Calendar
import java.util.Date
import kotlin.jvm.java

const val REQUEST_CODE = 5
class AlarmSchedular(val context: Context) {
    val alarmManager = ContextCompat.getSystemService(context, AlarmManager::class.java) as AlarmManager


    @SuppressLint("MissingPermission")
    fun setAlarmDaily(hours: Int, minutes: Int, onDone: (Boolean) -> Unit) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hours)
            set(Calendar.MINUTE, minutes)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        val alarmTime = calendar.timeInMillis

        val alarmIntent = Intent(context, AlarmReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarmTime,
            alarmPendingIntent
        )
        onDone(true)

        // Log for debugging
        Log.d("AlarmScheduler", "Alarm set for: ${Date(alarmTime)}")
    }
}