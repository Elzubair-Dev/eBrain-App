package com.example.ebrain.feature_task.domain.util.notification_alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.DelicateCoroutinesApi
import java.time.LocalDateTime
import java.time.ZoneId

@DelicateCoroutinesApi
class TaskAlarmScheduler(
    private val context: Context
): AlarmScheduler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun schedule(item: TaskAlarmItems) {

        val intent = Intent(context, NotificationAlarmReceiver::class.java).apply {
            putExtra("TITLE", item.title)
            putExtra("ID", item.id)
        }


        if (item.intervalValue != null){
            repeatingAlarmTrigger(item.time, item.id, item.intervalValue, intent)
        }else{
            alarmTrigger(item.time, item.id, intent)
        }

    }

    override fun cancel(item: TaskAlarmItems) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.id,
                Intent(context, NotificationAlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun alarmTrigger(time: LocalDateTime, id: Int, intent: Intent){
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            PendingIntent.getBroadcast(
                context,
                id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun repeatingAlarmTrigger(time: LocalDateTime, id: Int, interval: Long, intent: Intent){
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            1000 * 60 * interval,
            PendingIntent.getBroadcast(
                context,
                id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}