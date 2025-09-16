package com.example.miseryreminder.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.miseryreminder.receiver.ApplicationReceiver
import java.util.Calendar
import java.util.Date

const val REQUEST_CODE = 5
const val DAILY_REPEAT = "daily repeat"
const val ACTION_DAILY_ALARM = "com.berlin.ACTION_DAILY_ALARM"
const val HOURS = "hours"
const val MINUTES = "minutes"
class AlarmSchedular(val context: Context) {
    val alarmManager = ContextCompat.getSystemService(context, AlarmManager::class.java) as AlarmManager


    @SuppressLint("MissingPermission")
    fun setAlarmDaily(hours: Int, minutes: Int,repeat: Boolean = false, onDone: (Boolean, Calendar) -> Unit) {
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

        val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(DAILY_REPEAT, repeat)
            putExtra(HOURS, hours)
            putExtra(MINUTES, minutes)
            action = ACTION_DAILY_ALARM
        }
        val alarmPendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(alarmPendingIntent)

        val applicationIntent = Intent(context, ApplicationReceiver::class.java)
        val applicationPendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            applicationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarmTime + 1000 * 10,
            applicationPendingIntent
        )
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarmTime,
            alarmPendingIntent
        )
        onDone(true, calendar)

        // Log for debugging
        Log.d("AlarmScheduler", "Alarm set for: ${Date(alarmTime)}")
    }
    fun isAlarmSet(): Boolean {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            action = ACTION_DAILY_ALARM
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        val exists = pendingIntent != null
        Log.d("AlarmScheduler", "Checking alarm with REQUEST_CODE=$REQUEST_CODE: exists = $exists")
        return exists
    }

    fun cancelAlarms(){
        val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
            action = ACTION_DAILY_ALARM
        }
        val alarmPendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(alarmPendingIntent)
        Log.d("AlarmScheduler", "Alarm has been cancelled")

    }
}